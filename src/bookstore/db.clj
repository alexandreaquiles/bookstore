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

(def schema [{:db/ident :book/name
              :db/valueType :db.type/string
              :db/cardinality :db.cardinality/one
              :db/doc "The name of the book"}
             {:db/ident :book/price
              :db/valueType :db.type/double
              :db/cardinality :db.cardinality/one
              :db/doc "The price of the book"}])

(defn create-schema! [conn]
  (d/transact conn schema))

(defn open-connection! []
  (d/create-database db-uri)
  (d/connect db-uri))

(defn delete-db! []
 (d/delete-database db-uri))

(defn add-book! [conn book]
  (d/transact conn [book]))

(defn add-books! [conn books]
  (d/transact conn books))

; datomic.db.Db@16418ddb (it's some moment for this DB)
;(d/db conn)

(defn find-sicp [conn]
   (d/q '[:find ?entity-id
         :where [?entity-id :book/name "SICP"]] (d/db conn)))

(defn find-books-that-cost-99.9 [conn]
  (d/q '[:find ?entity-id
         :where [?entity-id :book/price 99.9]] (d/db conn)))

; won't work yet
;(defn find-entities-by-name [conn name]
;  (d/q '[:find ?entity-id
;         :where [?entity-id :book/name name]] (d/db conn)))

; won't work yet
;(defn find-entities-by-price [conn price]
;  (d/q '[:find ?e
;         :where [?e :book/price price]] (d/db conn)))

; data clause in :where (some thing are "open")
; [entity-id attribute value tx-id added?]