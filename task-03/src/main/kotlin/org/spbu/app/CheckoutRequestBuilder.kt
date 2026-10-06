package org.spbu.app

data class CheckoutRequest(
    val userId: Int,
    val sku: String,
    val qty: Int,
    val comment: String?
)

class CheckoutRequestBuilder {
    var userId: Int? = null
    var sku: String? = null
    var qty: Int? = null
    var comment: String? = null

    fun build(): CheckoutRequest {
        require(userId != null && userId!! > 0) { "userId must be positive" }
        require(sku != null && sku!!.trim().isNotEmpty()) { "sku cannot be blank" }
        require(qty != null && qty!! > 0) { "qty must be positive" }
        
        val cleanSku = sku!!.trim()
        val cleanComment = comment?.trim()?.takeIf { it.isNotEmpty() }
        
        return CheckoutRequest(
            userId = userId!!,
            sku = cleanSku,
            qty = qty!!,
            comment = cleanComment
        )
    }
}