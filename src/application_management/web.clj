(ns application-management.web
  (:require [compojure.core :refer [defroutes GET]]
            [ring.adapter.jetty :as ring])
)

(defroutes routes 
  (GET "/" [] "<H3>hello world</H3>"))

(defn -main []
  (ring/run-jetty #'routes {:port 8080 :join? false}))
