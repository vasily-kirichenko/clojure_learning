(ns clojure-learning.atom-performance)

(defn ref-watcher
  [every key ref old new]
  (if (zero? (mod new every))
    (prn (str (.getId (Thread/currentThread)) " " key ": the ref changed from " old " to " new "."))))

(defn with-watcher [w r] (add-watch r :logger w) r)

(defn parallel-repeat
  [threads f]
  (repeatedly threads #(fn [] (future (f)))))

(defn perform
  [threads repeats f]
  (let [futures (parallel-repeat threads #(dotimes [i repeats] (f i)))]
    (doseq [ft futures] @(ft))))

(let [threads 10
      repeats 1000
      watcher (partial ref-watcher (/ repeats threads))
      a (atom 0) ;(with-watcher watcher (atom 0))
      r (ref 0) ;(with-watcher watcher (ref 0))
      datas [["atom"        #(swap! a (constantly %))]
             ["ref-set"     #(dosync (ref-set r %))]
             ["commute ref" #(dosync (commute r (constantly %)))]
             ["alter ref"   #(dosync (alter r (constantly %)))]]]
  (doseq [[msg f] datas]
     (prn msg)
     (time (perform threads repeats f))))
