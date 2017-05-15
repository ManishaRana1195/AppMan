(ns application-management.server.db.redis
(:require   [clojure.string :as str]
            [taoensso.carmine :as car :refer (wcar)]))


(def server-conn {:pool {} :spec {:host "localhost"
                               :port     6379
                               :timeout-ms  4000}})

(defmacro wcar* [& body] `(car/wcar server-conn ~@body))

(defn save
  [email-id]
  (wcar* (car/lpush "applicants" email-id)))

(defn saveSet
  [email-id content]
  (wcar* (apply car/hmset email-id content)))

(defn convert-format
  [detailList]
 (apply hash-map detailList)
)

(defn getAll
  [type]  
  (loop [recent-emails (wcar* (car/lrange type 0 50))  
         applications []]
    (if (empty? recent-emails)
      applications
      (recur (pop recent-emails) (merge applications (convert-format (wcar* (car/hgetall (peek recent-emails))))))))
  )
