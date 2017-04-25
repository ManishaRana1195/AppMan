(ns application-management.controllers.applications
(:require [compojure.core :refer [defroutes GET POST]]
            [clojure.string :as str]
            [ring.util.response :as ring]
            [application-management.db.redis :as redis]
            ))

(defn invalid?
  [value]
  (or (empty? value) (nil? value)))

;; some returns nil if the all elements are valid
(defn invalid-json [application]
 (some invalid? (map second application)))

(defn get-ip []
  "")

(defn get-email-id [application]
  (get application "emailId"))

(defn get-struct
  [application]
  (flatten (seq application)))

(defn create
  [application]  
  (when (invalid-json application)
    {:error "Error" :message ""})
  (let [ip        get-ip 
        email-id  (get-email-id application)]
   (redis/save email-id)
   (redis/saveSet (get application "emailId") (get-struct application)))
  {:success true})


(defn getApplications
  []
  (redis/getAll))

(defn accept
  [])

(defn reject
  [])

(defn filterList
  [])

(defn login
  [])
