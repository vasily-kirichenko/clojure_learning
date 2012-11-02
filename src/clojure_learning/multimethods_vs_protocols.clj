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

;; multimethod

(defmulti m-total class)

(defmethod m-total Order
  [order]
  (:price order))

(defmethod m-total clojure.lang.IPersistentMap
  [m]
  (:price m))

;; test

(def n 50000000)

(defn measure
  [obj type]
  (let [msg (format "Do %s iterations on an %s:" n type)
        delim (apply str (repeat (count msg) "-"))]
    (println delim)
    (println msg)
    (println delim))

  (doseq [[f msg] [[:price      "direct (map)     "]
                   [p-total     "protocol         "]
                   [m-total     "multimethod (map)"]]]
    (println (format "%s : %s msecs." msg
               (try
                 (tc (dotimes [_ n] (f obj)))
                 (catch Exception ex (str ex)))))))

(measure (Order. 10) "order")
(measure {:price 10} "map")