(ns bookstore.core
  (:use clojure.pprint)
  (:require [datomic.api :as d]
            [bookstore.db :as b.db]))

(def conn (b.db/open-connection!))
;(println conn)

(b.db/create-schema! conn)

;(b.db/add-book! conn clojure-book)
;(b.db/add-book! conn sicp-book)

(let [clojure-book {:book/name  "Clojure for the Brave and True"
                    :book/price 99.9}
      sicp-book {:book/name "SICP"
                 :book/price 450.5}]
  (b.db/add-books! conn [clojure-book sicp-book]))

(b.db/find-sicp conn)

(b.db/find-books-that-cost-99.9 conn)

(b.db/delete-db!)
