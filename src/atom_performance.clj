(def a (atom 0))

(add-watch a :print-watcher
  (fn [key ref old new]
    (if (zero? (mod new 1000))
      (prn (str (Thread/currentThread) key ": the atom 'a' changed from " old " to " new ".")))))

(def n 10000)

(let [futures
      (->>
        (split-at (/ n 2) (range n))
        (map (fn [chunk]
               (fn []
                 (dotimes [_ (count chunk)]
                 (swap! a inc)))))
        (map #(future (%)))
        (doall))]

  (time (doall
    (reduce (fn [_ ft] @ft) futures))))

(prn @a)