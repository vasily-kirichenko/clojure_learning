(ns clojure-learning.atom-performance)

(def a (atom 0))
(def r (ref 0))
(def n 100000)
(def chunk-count 10)

;(add-watch a :print-watcher
;  (fn [key ref old new]
;    (if (zero? (mod new (/ n 10)))
;      (prn (str (.getId (Thread/currentThread)) " " key ": the atom 'a' changed from " old " to " new ".")))))

(defn perform
  [ref f]
  (let [futures
      (->>
        (range n)
        (partition-all (/ n chunk-count))
        (map #(future (dotimes [_ (count %)]
                        (f ref))))
        (doall))]
    (time
      (doall
        (reduce #(deref %2) futures)))))

(let [datas [["atom"        a #(swap! % inc)]
             ["ref-set"     r #(dosync (ref-set % 0))]
             ["commute ref" r #(dosync (commute % inc))]
             ["alter ref"   r #(dosync (alter % inc))]]]
  (doseq [[msg ref f] datas]
     (println msg)
     (perform ref f)))
