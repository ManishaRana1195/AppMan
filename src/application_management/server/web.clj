(ns application-management.server.web
  (:require [compojure.core :refer [defroutes GET POST]]
            [compojure.route :as route]
            [ring.adapter.jetty :as ring]
            [ring.middleware.params :as par]
            [ring.middleware.json :as json :only [wrap-json-body wrap-json-response wrap-json-params]]
            [ring.util.response :as resp]
            [compojure.handler :as handler]
            [application-management.server.controllers.applications :as app]
            [application-management.client.views :as views])
  (:gen-class)
)


(defroutes routes
  (route/resources "/")
  (GET "/" [] views/home-page)
  (GET "/all" [] (resp/response (app/getApplications)) )
  (POST "/apply" request (resp/response  (app/create (get-in request [:params]))))
  (POST "/accept" {body :body} (resp/response (app/accept body)))
  (POST "/reject" {body :body} (resp/response (app/reject body)))
  (POST "/filter" {params :params} (resp/response (app/filterList params)))
  (POST "/admin/login" {params :params} (resp/response (app/login params))))
  
(def app
  (-> (handler/site routes)
      (json/wrap-json-body)
      (json/wrap-json-params)
      (json/wrap-json-response)))

(defn -main []
  (ring/run-jetty app {:port 8080 :join? false}))
