(ns application-management.server.controllers.applications
(:require [compojure.core :refer [defroutes GET POST]]
            [clojure.string :as str]
            [ring.util.response :as ring]
            [application-management.server.db.redis :as redis]
            [application-management.server.email :as email]
            ))

(defn invalid?
  [value]
  (or (empty? value) (nil? value)))

;;some returns nil if the all elements are valid
(defn invalid-json [application]
 (some invalid? (map second application)))

(defn get-ip []
  "")

(defn get-email-id [application]
  (get application :emailId))

(defn get-struct
  [application]
  (flatten (seq application)))

(defn create
  [application] 
  (if (invalid-json application)
    {:error "Error" :message ""}
  
  (do (let [ip        get-ip 
            email-id  (get-email-id application)]
   (redis/save email-id)
   (redis/saveSet (get application :emailId ) (get-struct application)))
  {:success true})))

;;saving the details of the user into redis
 (defn save-details
  [details]
  (prn details)
  (if (invalid-json details) 
      {:error "Error" :message ""}
  (do (redis/saveSet (get details :emailId) (get-struct details))
      {:success "true"})))

(defn getApplications
  []
  (redis/getAll))

(defn accept
  [body]
  (email/accept body))

(defn reject
  [body]
  (email/reject body))

(defn filterList
  [body]
  (redis/getAll))

(defn login
  [])
