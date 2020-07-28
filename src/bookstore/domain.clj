(ns bookstore.domain)

;calling static method in Java
;(java.lang.Math/pow 2 3)
;(Math/pow 2 3)

(defn uuid []
  (java.util.UUID/randomUUID))

(defn new-book
  ([name price] (new-book (uuid) name price))
  ([uuid name price]
   {:book/id    uuid
    :book/name  name
    :book/price price}))

