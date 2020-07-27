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

(b.db/find-entity-ids-by-name conn "SICP")
(b.db/find-entity-ids-by-name conn "Clojure for the Brave and True")
(b.db/find-entity-ids-by-name conn "Book that doesn't exist")

(b.db/find-entity-ids-by-price conn 99.9)
(b.db/find-entity-ids-by-price conn 450.5)
(b.db/find-entity-ids-by-price conn 123.4)

(b.db/find-all-entity-ids-of-books conn)

(let [kafka-book {:book/name "Metamorphosis"}]
  (b.db/add-book! conn kafka-book))

; didn't get transacted into datomic
; :db.error/nil-value Nil is not a legal value
;(let [stu-book {:book/name "Programming Clojure"
;                :book/price nil}]
;  (b.db/add-book! conn stu-book))

(b.db/find-entity-ids-by-name conn "Metamorphosis")

(b.db/update-book! conn 17592186045421 :book/price 10.0)
;[:db/add entity-id attribute value]

(b.db/find-entity-ids-by-price conn 10.0)

;Promotion!
(b.db/update-book! conn 17592186045419 :book/price 225.25)

(b.db/find-entity-ids-by-price conn 450.5)
(b.db/find-entity-ids-by-price conn 225.25)

(let [stu-book {:book/name "Programming Clojure"
                :book/price 1000.0}]
  (b.db/add-book! conn stu-book))

(b.db/find-entity-ids-by-price conn 1000.0)

(b.db/find-all-prices conn)
;#{[99.9] [225.25] [10.0]}
;Clojure / SICP / Metamorphosis

(b.db/find-all-names conn)
;#{["Programming Clojure"] ["SICP"] ["Clojure for the Brave and True"] ["Metamorphosis"]}

(b.db/find-entity-ids-by-name conn "Programming Clojure")

(b.db/update-book! conn 17592186045425 :book/price 99.9)

(b.db/find-all-prices conn)
;#{[99.9] [225.25] [10.0]}

(b.db/find-all-price-with-entity-ids conn)

(b.db/find-all-names-with-entity-ids conn)

(b.db/find-all-prices-and-names-with-entity-ids conn)

(b.db/find-all-prices-and-names conn)
;#{["SICP" 225.25] ["Clojure for the Brave and True" 99.9] ["Programming Clojure" 99.9] ["Metamorphosis" 10.0]}

(b.db/find-all-books (d/db conn))
;=>
;[[{:db/id 17592186045425, :book/name "Programming Clojure", :book/price 99.9}]
; [{:db/id 17592186045418, :book/name "Clojure for the Brave and True", :book/price 99.9}]
; [{:db/id 17592186045419, :book/name "SICP", :book/price 225.25}]
; [{:db/id 17592186045421, :book/name "Metamorphosis", :book/price 10.0}]]

(def picture-from-the-past (d/db conn))
; => datomic.db.Db@398c5a7f

(let [joy-book {:book/name  "Joy of Clojure"
                    :book/price 400.0}
      applied-book {:book/name "Clojure Applied"
                 :book/price 450.5}]
  (b.db/add-books! conn [joy-book applied-book]))
; :db-before datomic.db.Db, @398c5a7f
; :db-after, datomic.db.Db @9ee30a36,


(def picture-from-now (d/db conn))

(b.db/find-all-books (d/db conn))
(b.db/find-all-books picture-from-the-past)
(b.db/find-all-books picture-from-now)

(b.db/find-all-books (d/as-of (d/db conn)
                              #inst "2014-05-19T19:12:37.925-00:00"))
(b.db/find-all-books (d/as-of (d/db conn)
                              #inst"2020-07-27T15:27:06.350-00:00"))
(b.db/find-all-books (d/as-of (d/db conn)
                              #inst"2020-07-27T14:58:57.789-00:00"))

;(b.db/delete-db!)
