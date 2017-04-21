(ns application-management.web
  (:require [compojure.core :refer [defroutes GET POST]]
            [compojure.route :as route]
            [ring.adapter.jetty :as ring]
            [ring.middleware.params :as par]
            [ring.middleware.json :as json :only [wrap-json-body wrap-json-response]]
            [ring.util.response :as resp]
            [application-management.controllers.applications :as app])
  (:gen-class)
)

(defroutes routes
  (GET "/" [] "<H3>hello world</H3>")
  (POST "/all" {body :body} (resp/response (app/getApplications body)) )
  (POST "/apply" {body :body}  (resp/response  (app/create body))))


(defn -main []
  (ring/run-jetty (json/wrap-json-body routes) {:port 8080 :join? false}))
