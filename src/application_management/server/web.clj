(ns application-management.server.web
  (:require [compojure.core :refer [defroutes GET POST]]
            [compojure.route :as route]
            [ring.adapter.jetty :as ring]
            [ring.middleware.params :as par]
            [ring.middleware.json :as json :only [wrap-json-body wrap-json-response]]
            [ring.util.response :as resp]
            [application-management.server.controllers.applications :as app])
  (:gen-class)
)

(defroutes routes
  (GET "/" [] "<H3>hello world</H3>")
  (GET "/all" [] (resp/response (app/getApplications)) )
  (POST "/apply" {body :body}  (resp/response  (app/create body)))
  (POST "/accept" {body :body} (resp/response (app/accept body)))
  (POST "/reject" {body :body} (resp/response (app/reject body)))
  (POST "/filter" {params :params} (resp/response (app/filterList params)))
  (POST "/admin/login" {params :params} (resp/response (app/login params))))
  


(defn -main []
  (ring/run-jetty (json/wrap-json-body routes) {:port 8080 :join? false}))
