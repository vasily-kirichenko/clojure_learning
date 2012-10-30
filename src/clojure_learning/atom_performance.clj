(ns clojure-learning.atom-performance)

(defn ref-watcher
  [every key ref old new]
  (if (zero? (mod new every))
    (prn (str (.getId (Thread/currentThread)) " " key ": the ref changed from " old " to " new "."))))

(defn parallel-repeat
  [f threads repeats]
  (->>
    (range threads)
    (map (fn [_] #(future
                    (dotimes [i repeats]
                      (f i)))))
    (doall)))

(defn perform
  [f threads repeats]
  (let [futures (parallel-repeat f threads repeats)]
    (doseq [ft futures] @(ft))))

(let [a (atom 0)
      r (ref 0)
      threads 10
      repeats 1000
      watcher (partial ref-watcher (/ repeats 10))
      datas [["atom"        #(swap! a (fn [_] %))]
             ["ref-set"     #(dosync (ref-set r %))]
             ["commute ref" #(dosync (commute r (fn [_] %)))]
             ["alter ref"   #(dosync (alter r (fn [_] %)))]]]
  (add-watch a :logger watcher)
  (add-watch r :logger watcher)
  (doseq [[msg f] datas]
     (prn msg)
     (time (perform f threads repeats))))
