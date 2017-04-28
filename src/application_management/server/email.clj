(ns application-management.server.email
	  (:require [sendgrid-clj.core :as grid]))

(def auth {:api_user "hey" :api_key "hey"})

(defn send-data
	[body]
(grid/send-email auth {
  :to "emailId.com"
  :from "santoshemail.com"
  :subject "Accept mail"
  :html "<h1>Hello world</h1>"
  :text "Hello world"}))



