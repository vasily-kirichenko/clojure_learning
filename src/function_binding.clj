(ns function-binding
  (:use fib))

(def fib-10
  (partial fib 10))

(defn with-logging
  [f msg]
  (fn [& args]
    (prn msg)
    (apply f args)))

(binding [fib (with-logging fib "calling fib...")]
  (fib-10))