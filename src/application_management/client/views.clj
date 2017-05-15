(ns application-management.client.views
  (:require [clojure.string :as str]
  			    [application-management.server.controllers.applications :as app]
            [hiccup.page :as hic]
            [hiccup.form :as form]))  

(defn gen-page-head
  [title]
  [:head
   [:title title]
   (hic/include-css "https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.2/css/materialize.min.css")
   (hic/include-css "css/style.css")
   (hic/include-js "https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js")
   (hic/include-js "js/compiled/app_man_cljs.js")
   (hic/include-js "https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.2/js/materialize.min.js")])

(def header-links
	[:nav [:div.nav-wrapper
		[:a.brand-logo { :href "/"} "AppMan"]
  		[:ul#nav-mobile.right.hide-on-med-and-down 
   			[:li [:a { :href "/"} "Applicants"] ]
   			[:li [:a { :href "/current"} "Gladiators"]]
   			[:li [:a { :href "/passed"} "Spartans"]]]
   	]])

(defn div-chip
  [language]
  [:div.chip.language-tag language])

(defn get-icons
  [link]
  (cond
    (str/includes? link "github")  "img/glogo.png"
    (str/includes? link "twitter")  "img/tlogo.png"
    :else "img/llogo.png"))

(defn div-image
  [link]
  [:a.link-img { :href link :target "_blank"} [:img {:src (get-icons link)}]]) 


(defn get-chips
  [languages]
  (let [lang-list (str/split languages #"\s+")]
    (map div-chip lang-list)))

(defn display-links
  [links]
  (let [link (str/split links #"\s+")]
    (map div-image link)))


(defn interview-view
  []
    [:div.interview-div
      [:span.input-inline (form/text-field {:placeholder "Time"} :time)]
      [:span.input-inline (form/text-field {:placeholder "Date"} :date) ]
      [:div.chip "or"]
      [:div.input-inline (form/text-field {:placeholder "Email Id"} :emailId) ]
      [:button {:class ""} "Ok "]])


(defn display-record
	[details]
    [:div.row.without-margin [:div.col.s12.card.blue-grey.darken-1 
        [:div.col.s9.card-content.white-text
          [:div.extra-margin
            [:span.inline-title (get details "name")]
            [:span.applicant-email (get details "emailId")]
            [:span.applicant-phone (get details "phoneNumber")]
            [:span.display-links (display-links (get details "links"))]
          ]
            [:p.eligible (get details "eligible")]]

        [:div.col.s3 [:div.chips.languages (get-chips (get details "languages"))]]
  
        [:div.col.s4
          [:div.card-action
          [:button.action-button {;;:on-click 
                                    ;;#(.setAttribute (.getElementById js/document (name "interview-view")) "display" "block")
                                             } "Accept"]
          [:button.action-button {:href "/reject"} "Reject"]]]
        
        [:div.col.s8  (interview-view)]]]
)

(defn get-footer
	[]
    [:footer.page-footer
    ; [:div.container [:div.row 
    ;   	[:div.col.l6.s12
    ;         [:h5.white-text "Footer Content"]
    ;         [:p.grey-text.text-lighten-4 "You can use rows and columns here to organize your footer content"]]

    ;     [:div.col.l4.offset-l2.s12
    ;         [:h5.white-text "Links"]
    ;         [:ul
    ;           [:li [:a.grey-text.text-lighten-3 { :href "/"} "Link 1"]]
			 ;  [:li [:a.grey-text.text-lighten-3 { :href "/"} "Link 2"]]]]]]

    [:div.footer-copyright [:div.container
        "© 2014 Copyright Text"
        [:a.grey-text.text-lighten-4.right { :href "/"} "More Links"]]]]
)

(defn show-no-result-view
  [result]
  [:div.row.without-margin 
    [:div.col.s12.card.blue-grey.darken-1.card-content.white-text "Sorry, No results found"]])

(defn show-result 
  [result]
  (map display-record result))

(defn page
  [result]
  (hic/html5
    [:header (gen-page-head "AppMan") header-links]
    [:main [:div.container.content-box  
        (if (empty? result)
        (show-no-result-view result)
        (show-result result))]]
    (get-footer))
  )

(defn applicants-page
  [req]
  (page (app/getApplications)))

(defn user-details
  [req]
  (hic/html5
    [:header 
      (gen-page-head "user-details")
       header-links]
       [:main 
        (form/form-to {:enctype "multipart/form-data"}[:post "/user/details"]
         [:div
           [:input.form-control
             {:type "text" :placeholder "Your name" :name "name"}]
               [:input.form-control
             {:type "text" :placeholder "Your email-id" :name "emailId"}]
               [:input.form-control
             {:type "text" :placeholder "Your description" :name "description"}]
             [:input.form-control
             {:type "file" :accept "pdf" :required "true" :name "idProof"}]
             [:input.datepicker
             {:type "date"}]]
             (form/submit-button {:class "btn"} "Save"))])
             )

(defn current-batch-page
  [req]
  (page (app/getCurrent)))

(defn passed-batch-page
  [req]
  (page (app/getPassed)))
