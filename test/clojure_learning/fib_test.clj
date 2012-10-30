(ns clojure-learning.fib_test
  (:use clojure.test
        clojure-learning.fib))

(deftest compare-to-old
  (dotimes [n 20]
    (is (= (fib n) (ffib n)) n)))

(run-tests)

