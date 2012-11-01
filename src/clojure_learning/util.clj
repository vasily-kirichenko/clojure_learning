(ns clojure-learning.util)

(defmacro tc
  "Evaluates expr and returns the time it took."
  [expr]
  `(let [start# (System/nanoTime)]
     ~expr
     (/ (double (- (System/nanoTime) start#)) 1000000.0)))

