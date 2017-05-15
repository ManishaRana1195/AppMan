(ns application-management.server.web
  (:require [compojure.core :refer [defroutes GET POST]]
            [compojure.route :as route]
            [ring.adapter.jetty :as ring]
            [ring.middleware.params :as par]
            [ring.middleware.json :as json :only [wrap-json-body wrap-json-response wrap-json-params]]
            [ring.util.response :as resp]
            [compojure.handler :as handler]
            [application-management.server.controllers.applications :as app]
            [application-management.client.views :as views]
            [clojure.java.io :as io])
  (:gen-class)
)

(defn save-idProof [source-path dest-path]
  (io/copy (io/file source-path) (io/file dest-path)))

(defn get-dest-path [file-name]
  (format "data/idProof/%s.pdf" file-name))

(defn get-src-path [request]  
  (str (get-in request [:params :idProof :tempfile])))

(defn update-params [request] 
      (let [new-request (assoc-in request [:params :idProof] (get-dest-path (get-in request [:params :emailId])))]
      (get-in new-request [:params])))

(defroutes routes
  (route/resources "/")
  (GET "/" [] views/applicants-page)
  (GET "/all" [] (resp/response (app/getApplications)))
  (GET "/form" [] views/user-details)
  (GET "/passed" [] views/passed-batch-page)
  (GET "/current" [] views/current-batch-page)
  (POST "/apply" request (resp/response  (app/create (get-in request [:params]))))
  (POST "/accept" request (resp/response (app/accept (get-in request [:params]))))
  (POST "/reject" request (resp/response (app/reject (get-in request [:params]))))
  (POST "/filter" request (resp/response (app/filterList (get-in request [:params]))))
  (POST "/admin/login" {params :params} (resp/response (app/login params)))
  (POST "/user/details" request
                              (save-idProof (get-src-path request) (get-dest-path (get-in request [:params :emailId])))
                              (resp/response (app/save-details (update-params request)))))
  




(def app
  (-> (handler/site routes)
      (json/wrap-json-body)
      (json/wrap-json-params)
      (json/wrap-json-response)))

(defn -main []
  (ring/run-jetty app {:port 8080 :join? false}))

