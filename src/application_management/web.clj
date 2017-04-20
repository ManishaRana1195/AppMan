(ns application-management.web
  (:require [compojure.core :refer [defroutes GET POST]]
            [compojure.route :as route]
            [ring.adapter.jetty :as ring]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [application-management.controllers.applications :as app])
  (:gen-class)
)

(defroutes routes
  (GET "/" [] "<H3>hello world</H3>")
  (GET "/all" [] (app/getApplications) )
  (POST "/applications" {params :params} (app/create params))
   (route/resources "/"))

(def settings
  (wrap-defaults routes site-defaults))

(defn -main []
  (ring/run-jetty #'routes {:port 8080 :join? false}))
