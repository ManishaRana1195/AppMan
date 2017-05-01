(ns application-management.client.views
  (:require [clojure.string :as str]
  			[application-management.server.controllers.applications :as app]
            [hiccup.page :as hic]))

(defn gen-page-head
  [title]
  [:head
   [:title title]
   (hic/include-css "css/style.css")
   (hic/include-css "https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.2/css/materialize.min.css")
   (hic/include-js "https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js")
   (hic/include-js "js/compiled/app_man_cljs.js")
   (hic/include-js "https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.2/js/materialize.min.js")])

(def header-links
	[:nav [:div.nav-wrapper
		[:a.brand-logo { :href "/"} "AppMan"]
  		[:ul#nav-mobile.right.hide-on-med-and-down 
   			[:li [:a { :href "/"} "Applicants"] ]
   			[:li [:a { :href "/current-batch"} "Currently Surviving"]]
   			[:li [:a { :href "/passed-batch"} "Survivors"]]]
   	]])

(defn display-record
	[details]
    [:div.row [:div.col.s12.card.blue-grey.darken-1 
        [:div.col.s10
        [:div.card-content.white-text
          [:span.card-title (get details "name")]
          [:span.applicant-email (get details "emailId")]
          [:span.applicant-phone (get details "phoneNumber")]
          [:p (get details "eligible")]]]

        [:div.col.s2
            [:div.card-action
          [:a.action-button {:href "#"} "Accept"]
          [:a.action-button {:href "#"} "Reject"]
        ]]]]
)


(defn get-footer
	[]
    [:footer.page-footer
    [:div.container [:div.row 
      	[:div.col.l6.s12
            [:h5.white-text "Footer Content"]
            [:p.grey-text.text-lighten-4 "You can use rows and columns here to organize your footer content"]]

        [:div.col.l4.offset-l2.s12
            [:h5.white-text "Links"]
            [:ul
              [:li [:a.grey-text.text-lighten-3 { :href "/"} "Link 1"]]
			  [:li [:a.grey-text.text-lighten-3 { :href "/"} "Link 2"]]]]]]

    [:div.footer-copyright [:div.container
        "© 2014 Copyright Text"
        [:a.grey-text.text-lighten-4.right { :href "/"} "More Links"]]]]
)

(defn home-page
  [req]
  (hic/html5
    [:header 
   		(gen-page-head "YourAppMan")
   		header-links ]
    [:main 
   		[:div.container (let [resp (app/getApplications)]
 							(map display-record resp))]]
    (get-footer)
))
