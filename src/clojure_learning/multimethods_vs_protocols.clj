(ns clojure-learning.multimethods-vs-protocols)

(defrecord Order [price])

;; protocol

(defprotocol Calculable
  (ptotal [this]))

(extend-protocol Calculable
  Order
  (ptotal [order] (:price order)))

;; multimethod

(defmulti mtotal class)

(defmethod mtotal Order [order]
  (:price order))

;; test

(let [orders (reduce conj (for [n (range 20000000)] (Order. n)) ())]
  (prn (format "created %s orders." (count orders)))

  (time
    (doseq
      [o orders]
      (ptotal o)))
  (prn "protocol done.")

  (time
    (doseq
      [o orders]
      (mtotal o)))
  (prn "multimethod done."))