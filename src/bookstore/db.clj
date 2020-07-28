(ns bookstore.db
  (:require [datomic.api :as d]))

; SCHEMA is a datom in Datomic
; entity-id -> 72
; attribute -> 10 :db/ident
; value -> :book/name
; tx-id -> 13194139534312
; add? -> true

; BOOK is a datom in Datomic

; #datom[17592186045418 72 "Clojure for the Brave and True" 13194139534313 true]
; entity-id -> 17592186045418
; attribute -> 72 :book/name
; value -> "Clojure for the Brave and True"
; tx-id -> 13194139534313
; add? -> true

; #datom[17592186045418 73 9.99 13194139534313 true]
; entity-id -> 17592186045418
; attribute -> 73 :book/price
; value -> 9.99
; tx-id -> 13194139534313
; add? -> true

; #datom [entity-id attribute value tx-id add?]


;#object[datomic.promise$settable_future$reify__7382
;        0x468c92cc
;        {:status :ready,
;         :val {:db-before datomic.db.Db,
;               @9dc5beef :db-after,
;               datomic.db.Db @16418ddb,
;               :tx-data [#datom[13194139534317 50 #inst"2020-07-24T14:36:22.331-00:00" 13194139534317 true]
;                         #datom[17592186045422 72 "SICP" 13194139534317 true]
;                         #datom[17592186045422 73 450.5 13194139534317 true]],
;               :tempids {-9223301668109598135 17592186045422}}}]

; :tx-data [#datom[13194139534315 50 #inst"2020-07-24T14:34:00.636-00:00" 13194139534315 true]
;                         #datom[17592186045420 72 "Clojure for the Brave and True" 13194139534315 true]
;                         #datom[17592186045420 73 99.9 13194139534315 true]],

; result of the transact of the schema
;#object[datomic.promise$settable_future$reify__7382
;        0x2fef55f9
;        {:status :ready,
;         :val {:db-before datomic.db.Db,
;               @95ad4c6a :db-after,
;               datomic.db.Db @e944d5d4,
;               :tx-data [#datom[13194139534312 50 #inst"2020-07-24T13:50:28.777-00:00" 13194139534312 true]
;                         #datom[72 10 :book/name 13194139534312 true]
;                         #datom[72 40 23 13194139534312 true]
;                         #datom[72 41 35 13194139534312 true]
;                         #datom[72 62 "The name of the book" 13194139534312 true]
;                         #datom[73 10 :book/price 13194139534312 true]
;                         #datom[73 40 57 13194139534312 true]
;                         #datom[73 41 35 13194139534312 true]
;                         #datom[73 62 "The price of the book" 13194139534312 true]
;                         #datom[0 13 72 13194139534312 true]
;                         #datom[0 13 73 13194139534312 true]],
;               :tempids {-9223301668109598139 72, -9223301668109598138 73}}}]
;(d/transact conn [clojure-book])
;=>
;#object[datomic.promise$settable_future$reify__7382
;        0x646ccaad
;        {:status :ready,
;         :val {:db-before datomic.db.Db,
;               @afebdb5b :db-after,
;               datomic.db.Db @ee2de7dc,
;               :tx-data [#datom[13194139534313 50 #inst"2020-07-24T13:51:18.739-00:00" 13194139534313 true]
;                         #datom[17592186045418 72 "Clojure for the Brave and True" 13194139534313 true]
;                         #datom[17592186045418 73 9.99 13194139534313 true]],
;               :tempids {-9223301668109598137 17592186045418}}}]


(def db-uri "datomic:dev://localhost:4334/bookstore")

(def schema [
             ; a book
             {:db/ident       :book/id
              :db/valueType   :db.type/uuid
              :db/cardinality :db.cardinality/one
              :db/doc         "The UUID of the book"
              :db/unique      :db.unique/identity}
             {:db/ident       :book/name
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one
              :db/doc         "The name of the book"}
             {:db/ident       :book/price
              :db/valueType   :db.type/double
              :db/cardinality :db.cardinality/one
              :db/doc         "The price of the book"}
             {:db/ident       :book/categories
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/many
              :db/doc         "The categories of the book"}
             {:db/ident :book/publisher
              :db/valueType :db.type/ref
              :db/cardinality :db.cardinality/one
              :db/doc "The publisher of the book"}

             ; a publisher
             {:db/ident :publisher/id
              :db/valueType :db.type/uuid
              :db/cardinality :db.cardinality/one
              :db/unique :db.unique/identity}
             {:db/ident :publisher/name
              :db/valueType :db.type/string
              :db/cardinality :db.cardinality/one}])

(defn create-schema! [conn]
  (d/transact conn schema))

(defn open-connection! []
  (d/create-database db-uri)
  (d/connect db-uri))

(defn delete-db! []
 (d/delete-database db-uri))

(defn add-book! [conn book]
  (d/transact conn [book]))

(defn add-publisher! [conn publisher]
  (d/transact conn [publisher]))

(defn add-books! [conn books]
  (d/transact conn books))

(defn update-book! [conn entity-id attribute value]
  (d/transact conn [[:db/add entity-id attribute value]]))

;(d/transact conn [[:db/retract 17592186045425 :book/price]])

; won't work
; transact with the list form (I need the lookup ref)
;(d/transact conn [[:db/add
;                   #uuid"b1f4cf1b-d0d5-4faf-b7e1-ea2c2fbc5708"
;                   :book/name
;                   "Getting Clojure"]])

;doesn't need the lookup ref for the map form
;(d/transact conn [{:book/id #uuid"b1f4cf1b-d0d5-4faf-b7e1-ea2c2fbc5708"
;                   :book/name "Getting Clojure"}])


; datomic.db.Db@16418ddb (it's some moment for this DB)
;(d/db conn)

;(defn find-sicp [conn]
;   (d/q '[:find ?e
;         :where [?e :book/name "SICP"]] (d/db conn)))

;(defn find-books-that-cost-99.9 [conn]
;  (d/q '[:find ?e
;         :where [?e :book/price 99.9]] (d/db conn)))

(defn find-all-entity-ids-of-books [conn]
  (d/q '[:find ?e
         :in $                                              ; implicit
         :where [?e :book/price]]
       (d/db conn)))

(defn find-entity-ids-by-name [conn name-parameter]
  (d/q '[:find ?entity-id
         :in $ ?name                                        ; need to pass db ($) explicitly
         :where [?entity-id :book/name ?name]]
       (d/db conn)
       name-parameter))

(defn find-entity-ids-by-price [conn price-parameter]
  (d/q '[:find ?e
         :in $ ?price
         :where [?e :book/price ?price]]
       (d/db conn)
       price-parameter))

(defn find-all-prices [conn]
  (d/q '[:find ?price
         :where [_ :book/price ?price]]
       (d/db conn)))

(defn find-all-names [conn]
  (d/q '[:find ?name
         :where [_ :book/name ?name]]
       (d/db conn)))

(defn find-all-price-with-entity-ids [conn]
  (d/q '[:find ?e ?price
         :where [?e :book/price ?price]]
       (d/db conn)))

(defn find-all-names-with-entity-ids [conn]
  (d/q '[:find ?e ?name
         :where [?e :book/name ?name]]
       (d/db conn)))

(defn find-all-prices-and-names-with-entity-ids [conn]
  (d/q '[:find ?e ?name ?price
         :where [?e :book/name ?name]
                [?e :book/price ?price]]
       (d/db conn)))

;this will get a Cross Join
;[_ :book/name ?name]
;[_ :book/price ?price]
(defn find-all-prices-and-names [conn]
  (d/q '[:find ?name ?price
         :keys :book/name :book/price
         :where [?e :book/name ?name]
                [?e :book/price ?price]]
       (d/db conn)))

(defn find-all-books [db]
  (d/q '[:find (pull ?e [*])
         :where [?e :book/name ]]
       db))

(defn find-all-books-that-cost-more-than [db minimum-price]
  (d/q '[:find (pull ?e [*])
         :in $ ?min-price
         :where [?e :book/price ?price]
                [(>= ?price ?min-price)]]
       db minimum-price))

(defn find-all-books-by-category [db searched-category]
  (d/q '[:find (pull ?e [*])
         :in $ ?category
         :where [?e :book/categories ?category]]
       db searched-category))

(defn find-book-by-id [db book-id]
  (d/pull db '[*] book-id))

(defn find-all-publishers [db]
  (d/q '[:find (pull ?e [*])
         :where [?e :publisher/id]]
       db))

;(defn find-all-books-that-cost-more-than [db minimum-price]
;  (d/q '[:find ?e ?name ?price
;         :in $ ?min-price
;         :keys :db/id :book/name :book/price
;         :where [?e :book/price ?price]
;         [(>= ?price ?min-price)]
;         [?e :book/name ?name]]
;       db minimum-price))

; buy more books from the publisher
; query price > 200 & quantity < 5

; 10k books
;   - 5k have prices > 200
;   - just 10 have quantity < 5

; -> filtering 5k and then getting 10 => only 3 books
; -> filtering 10 and then getting cost > 200 => only 3 books

; data clause in :where (some thing are "open")
; [entity-id attribute value tx-id added?]
