(ns clojure-learning.runner
  (:use clojure.test)
  (:require clojure-learning.fib-test))

(run-all-tests #"clojure-learning\..*-test")
