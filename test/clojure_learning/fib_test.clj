(ns clojure-learning.fib-test
  (:use clojure.test
        clojure-learning.fib))

(deftest compare-to-old
  (dotimes [n 20]
    (is (= (fib n) (fast-fib n)) n)))

