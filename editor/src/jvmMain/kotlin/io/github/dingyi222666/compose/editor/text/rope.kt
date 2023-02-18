package io.github.dingyi222666.compose.editor.text

/**
 * @author: dingyi
 * @date: 2023/2/14
 * @description:
 **/


// change the data type from CharArray to StringBuilder
internal data class RopeNode(
    var left: RopeNode?, var right: RopeNode?, val weight: Int, val data: CharSequence? = null
)

data class MutablePair<L, R>(
    var left: L, var right: R
) {
    fun set(left: L, right: R) {
        this.left = left
        this.right = right
    }
}

class SubCharSequence(
    val source: CharSequence,
    val startIndex: Int,
    val endIndex: Int
) : CharSequence {
    override val length: Int = endIndex - startIndex

    override fun get(index: Int): Char {
        return source[index + startIndex]
    }

    override fun toString(): String {
        return source.subSequence(startIndex, endIndex).toString()
    }

    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence {
        if (startIndex == 0 && endIndex == length) {
            return this
        }
        return SubCharSequence(source, startIndex + this.startIndex, endIndex + this.startIndex)
    }
}

class MergeCharSequence(
    val left: CharSequence,
    val right: CharSequence
) : CharSequence {

    override val length: Int = left.length + right.length

    override fun get(index: Int): Char {
        return if (index < left.length) {
            left[index]
        } else {
            right[index - left.length]
        }
    }


    override fun toString(): String {
        return StringBuilder().apply {
            append(left)
            append(right)
        }.toString()
    }

    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence {
        if (startIndex == 0 && endIndex == length) {
            return this
        }
        if (startIndex < left.length) {
            if (endIndex < left.length) {
                return SubCharSequence(left, startIndex, endIndex)
            } else {
                return MergeCharSequence(
                    left.subSequence(startIndex, left.length),
                    right.subSequence(0, endIndex - left.length)
                )
            }
        } else {
            return SubCharSequence(right, startIndex - left.length, endIndex - left.length)
        }
    }
}

class Rope internal constructor(private val root: RopeNode) : CharSequence {

    override val length: Int = root.weight

    private val pair = MutablePair<RopeNode?, RopeNode?>(null, null)

    private var needSize: Int = maxSize * 2

    constructor(source: CharSequence) : this(buildTree(source))

    private fun connect(left: RopeNode?, right: RopeNode?): RopeNode? {
        if (left == null && right == null) {
            return null
        }
        if (right == null) {
            return left
        }

        if (left == null) {
            return right
        }

        // If the sum of the weights is smaller than or equal to the maximum size, and both nodes have data, merge them
        if (left.weight + right.weight <= maxSize && left.data != null && right.data != null) {
            return RopeNode(
                null,
                null,
                left.weight + right.weight,
                createMergeCharSequence(left.data, right.data)
            )
        }

        if (left.data != null || right.data != null) {
            val newLeft = if (left.data == null) {
                left
            } else {
                split(left, left.weight / 2)
                connect(pair.left, pair.right)!!
            }

            val newRight = if (right.data == null) {
                right
            } else {
                split(right, right.weight / 2)
                connect(pair.left, pair.right)!!
            }

            return RopeNode(newLeft, newRight, newLeft.weight + newRight.weight, null)
        }

        return if (left.weight > right.weight) {
            RopeNode(
                left.left, connect(left.right, right), left.weight + right.weight,
                null
            )
        } else {
            RopeNode(
                connect(left, right.left), right.right, left.weight + right.weight,
                null
            )
        }
    }

    private fun split(node: RopeNode, index: Int): MutablePair<RopeNode?, RopeNode?> {
        if (node.data != null) {
            // println("split data $index ${node.weight} ${node.weight - index} ${node.left?.weight} ${node.right?.weight}")
            val left = RopeNode(null, null, index, createSubCharSequence(node.data, 0, index))
            //  val right = RopeNode(null, null, node.weight - index, createSubCharSequence(node.data, index, node.weight))
            val right = RopeNode(
                null,
                null,
                node.weight - index,
                createSubCharSequence(node.data, index, minOf(node.weight, node.data.length))
            )
            pair.set(left, right)
            return pair
        } // If the node does not have data, it is an internal node
        else {
            // Compare the index with the weight of the left child
            if (index <= (node.left?.weight ?: -1)) {
                // Split the left child at the index
                val (left1, left2) = split(node.left!!, index)
                // Keep the right child as it is
                val right = node.right
                // Return the pair of the split left child and the right child
                pair.set(left1, RopeNode(left2, right, node.weight - index, null))
            } else {
                // Split the right child at the index minus the weight of the left child
                val (right1, right2) = split(node.right!!, index - node.left!!.weight)
                // Keep the left child as it is
                val left = node.left
                // Return the pair of the left child and the split right child
                pair.set(RopeNode(left, right1, index, null), right2)
            }
            return pair
        }


    }

    fun insert(source: CharSequence): Rope {
        return insert(length, source)
    }

    fun insert(index: Int, originSource: CharSequence): Rope {
        val source = if (originSource is StringBuilder) {
            originSource.toString()
        } else originSource

        if (index == 0) {
            return Rope(connect(buildTree(source), root)!!)
        }
        val (left, right) = split(root, index)
        val sourceNode = buildTree(source)
        var newRoot = connect(connect(left, sourceNode), right)!!

        if (newRoot.weight > needSize) {
            newRoot = balanceNode(newRoot)
        }

        return Rope(newRoot).apply {
            if (newRoot.weight > needSize) this.needSize = (needSize * 1.5).toInt()
        }
    }

    fun balance(): Rope {
        return Rope(balanceNode(root))
    }

    private fun balanceNode(node: RopeNode): RopeNode {
        val data = node.data

        if (data != null && data.length > maxSize) {
            val left = RopeNode(null, null, maxSize, createSubCharSequence(data, 0, maxSize))
            val right = RopeNode(null, null, data.length - maxSize, createSubCharSequence(data, maxSize, data.length))
            return RopeNode(left, right, left.weight + right.weight, null)
        }

        if (node.left != null) {
            node.left = balanceNode(node.left!!)
        }

        if (node.right != null) {
            node.right = balanceNode(node.right!!)
        }
        return node
    }

    fun delete(start: Int, end: Int): Rope {
        if (start == 0 && end == length) {
            return EMPTY
        }

        // If the start index is not zero and the end index is the length of the Rope, return the left part
        if (start > 0 && end == length) {
            // Split the Rope at the start index
            val (left, _) = split(root, start)
            // Return the left part
            return Rope(left!!)
        }
        // If the start index is zero and the end index is not the length of the Rope, return the right part
        else if (start == 0 && end < length) {
            // Split the Rope at the end index
            val (left, right) = split(root, end)
            // Return the right part
            return Rope(right!!)
        }
        // If the start index is not zero and the end index is not the length of the Rope, return the connection of the left and right parts
        else {
            // Split the Rope at the start index
            val (left1, right1) = split(root, start)
            // Split the right part at the end index minus the start index
            val (left2, right2) = split(right1!!, end - start)
            // Connect the left and right parts
            val newRoot = connect(left1, right2)
            // Return the new Rope
            return Rope(newRoot!!)
        }
    }

    override fun get(index: Int): Char {
        var node = root
        var i = index
        while (node.data == null) {
            if (node.left!!.weight > i) {
                node = node.left!!
            } else {
                i -= node.left!!.weight
                node = node.right!!
            }
        }
        return node.data!![i]
    }


    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence {
        if (startIndex == 0 && endIndex == length) {
            return this
        }
        val (left, right) = split(root, startIndex)
        val (middle, _) = split(right!!, endIndex - startIndex)
        return Rope(middle!!)
    }


    override fun toString(): String {
        val sb = StringBuilder()
        fun traverse(node: RopeNode) {
            if (node.data != null) {
                sb.append(node.data)
            } else {
                if (node.left != null) traverse(node.left!!)
                if (node.right != null) traverse(node.right!!)
            }
        }
        traverse(root)
        return sb.toString()
    }

    companion object {

        private const val maxSize = 199

        private val EMPTY = Rope("")

        private fun buildTree(source: CharSequence): RopeNode {
            if (source.isEmpty()) {
                return RopeNode(null, null, 0, null)
            }
            if (source.length <= maxSize) {
                return RopeNode(null, null, source.length, source)
            }
            val mid = source.length / 2

            var left: RopeNode? = null
            var right: RopeNode? = null

            if (mid > 0) {
                left = buildTree(createSubCharSequence(source, 0, mid))
            }
            if (mid < source.length && source.length - mid > 0) {
                right = buildTree(createSubCharSequence(source, mid, source.length))
            }

            return RopeNode(left, right, source.length, null)
        }

        private fun createSubCharSequence(source: CharSequence, start: Int, end: Int): CharSequence {
            if (start == end) {
                return ""
            }

            if (start > end) {
                println("start = $start, end = $end")
                throw IllegalArgumentException("start > end")
            }

            if (end - start == 1) {
                return source[start].toString()
            }
            if (source is SubCharSequence) {
                val newStart = source.startIndex + start
                val newEnd = source.startIndex + end

                if (newStart == 0 && newEnd == source.source.length) {
                    return source.source
                }
                //println("new start = $newStart, new end = $newEnd, source = ${source.source}")
                return SubCharSequence(source.source, newStart, newEnd)
            }

            if (source is MergeCharSequence) {
                return source.subSequence(start, end)
            }

            if (start == 0 && end == source.length) {
                return source
            }
            return SubCharSequence(source, start, end)
        }

        private fun createMergeCharSequence(left: CharSequence, right: CharSequence): CharSequence {
            if (left is MergeCharSequence && right is MergeCharSequence) {
                return MergeCharSequence(left.left, MergeCharSequence(left.right, right))
            }
            if (right is MergeCharSequence) {
                return MergeCharSequence(left, MergeCharSequence(right.left, right.right))
            }
            if (left.isEmpty()) {
                return right
            }
            if (right.isEmpty()) {
                return left
            }
            return MergeCharSequence(left, right)
        }
    }


}
