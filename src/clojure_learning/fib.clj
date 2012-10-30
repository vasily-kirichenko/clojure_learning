(ns clojure-learning.fib)

(defn fib
  "Calculate Fibonacci sequence using very slow straitforward algorithm."
  [n]
  (if (< n 2)
    1
    (+ (fib (- n 1)) (fib (- n 2)))))

(defn fast-fib
  "Calculate Fibonacci sequence using optimized algorithm."
  ([n]
    (if (< n 2)
      1
      (fast-fib 1 1 n 2)))

  ([n-2 n-1 n pos]
    (if (>= pos n)
      (+ n-1 n-2)
      (recur n-1 (+ n-2 n-1) n (inc pos)))))

(defmacro prn-func-call
  [f & args]
  `(prn (str "(" '~f " " ~@args ") = " (apply ~f (vec (list ~@args))))))

;(let [n 3]
;  ;(clojure.core/prn (clojure.core/str "(" (quote fib) " " n ") = " (clojure.core/apply fib (clojure.core/vec (list n))))))
;  (prn (macroexpand-1 '(prn-func-call fib n))))
;
;(let [n 37
;      f (future (time (prn-func-call fib n)))
;      n (future (time (prn-func-call ffib n)))]
;      [@f @n])
;
;(prn-func-call + 1 2 3)
