(ns fib
  (:use clojure.test))

(defn ^:dynamic fib
  [n]
  (if (< n 2)
    n
    (+ (fib (- n 1)) (fib (- n 2)))))

(defn ffib
  ([n]
    (case n
      0 0
      1 1
      (ffib 0 1 n 2)))

  ([n-2 n-1 n i]
    (if (>= i n)
      (+ n-1 n-2)
      (recur n-1 (+ n-2 n-1) n (inc i)))))

(deftest compare-to-old
  (dotimes [n 20]
      (is (= (fib n) (ffib n)) n)))

(run-tests)

(defmacro prn-func-call
  [f & args]
  `(prn (str "(" '~f " " ~@args ") = " (apply ~f (vec (list ~@args))))))

(let [n 3]
  ;(clojure.core/prn (clojure.core/str "(" (quote fib) " " n ") = " (clojure.core/apply fib (clojure.core/vec (list n))))))
  (prn (macroexpand-1 '(prn-func-call fib n))))

;(prn
;  (str
;    "("
;    'fib
;    " "
;    2
;    ") = "
;    (apply fib '(2))))

(let [n 37
      f (future (time (prn-func-call fib n)))
      n (future (time (prn-func-call ffib n)))]
      [@f @n])

(prn-func-call + 1 2 3)
