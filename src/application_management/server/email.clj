(ns application-management.server.email
	  (:require [sendgrid-clj.core :as grid]
	  	[hiccup.page :as hic]))

;;you have to give the sendgrid keys : the user and api key
(def auth {:api_user "" :api_key ""})

(defn reject-template [user-detail]
	(hic/html5
		[:head "GeekSkool"]
		[:div.container 
		[:p (str "Hello, " (get user-detail :name)) ]
		[:p "we won't be proceeding with your application."]
		[:p "Regards GeekSkool."]]))


(defn accept-template [user-detail]
	(hic/html5
		[:head "GeekSkool"]
		[:div.container 
		[:p (str "Hello " (get user-detail :name) "," )]
		[:p " we have accepted your application."]
		[:p " Your interview is scheduled at " (get (get-in user-detail [:interview-details]) :time) " " (get (get-in user-detail [:interview-details]) :date) ]
		[:p "Regards GeekSkool."]]))


(defn send-data
	[user body]
(grid/send-email auth {
  :to (get user :emailId)
  :from "info@geekskool.com"
  :subject "GEEKSKOOL APPLICATION"
  :html body
  :text "Hello world"}))


(defn accept
	[user-detail]
	;;change the status of application as accepted
(send-data user-detail (accept-template user-detail)))

(defn reject
  [user-detail]
  ;;change the status of application as rejected
(send-data user-detail (reject-template user-detail)))

