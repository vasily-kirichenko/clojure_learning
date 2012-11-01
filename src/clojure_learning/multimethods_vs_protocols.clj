(ns clojure-learning.multimethods-vs-protocols
  (:use clojure-learning.util))

(defrecord Order [price])

;; protocol

(defprotocol Calculable
  (p-total [this]))

(extend-protocol Calculable
  Order
  (p-total [order] (:price order))

  clojure.lang.IPersistentMap
  (p-total [m] (:price m)))

;; multimethod that accesses the record as a map

(defmulti m-map-total class)
(defmethod m-map-total Order [order] (:price order))
(defmethod m-map-total clojure.lang.IPersistentMap [m] (:price m))

;; multimethod that accesses the record as a class

(defmulti m-class-total class)
(defmethod m-class-total Order [order] (.price order))

;; test

(defn measure
  [constructor type]
  (let [orders (reduce conj (for [n (range 3000000)] (constructor n)) ())]
    (prn (format "created %s %ss." (count orders) type))

    (doseq [[f msg] [[:price "direct (map)"]
                     [p-total "protocol"]
                     [m-map-total "multimethod (map)"]
                     [#(.price %) "direct (class)"]
                     [m-class-total "multimethod (class)"]]]
      (prn (format
             "%s: %s msecs."
             msg
             (try
               (tc (dorun (map f orders)))
               (catch Exception ex (str ex))))))))

(measure #(Order. %) "order")
(measure #(hash-map :price %) "map")
