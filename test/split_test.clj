(ns clojure-learning.test
  (:use clojure.test
        clojure-learning))

(testing "simple cases"
  (deftest splits-empty-seq-to-single-empty-seq
    (are [n coll res] (= res (split n coll))
      1 () ()
      1 [] ())))






(run-tests)