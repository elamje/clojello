(ns clojello.views
    (:require [clojello.db :as db]
              [clojure.string :as str]
              [hiccup.page :as page]
              [ring.util.anti-forgery :as util]))
  
  (defn gen-page-head
    [title]
    [:head
     [:title (str "File: " title)]
     (page/include-css "/css/styles.css")])
  
  (def header-links
    [:div#header-links
     "[ "
     [:a {:href "/"} "Home"]
     " | "
     [:a {:href "/add-file"} "Add a File"]
     " | "
     [:a {:href "/all-files"} "View All Files"]
     " ]"])
  
  (defn home-page
    []
    (page/html5
     (gen-page-head "Home")
     header-links
     [:h1 "Home"]
     [:p "Webapp for file uploading through GUI"]))
  
  (defn add-file-page
    []
    (page/html5
     (gen-page-head "Add a File")
     header-links
     [:h1 "Add a File"]
     [:form {:action "/add-file" :method "POST"}
      (util/anti-forgery-field)
      [:p "file value: " [:input {:type "text" :name "file"}]]
      [:p "user value: " [:input {:type "text" :name "user"}]]
      [:p [:input {:type "submit" :value "submit file"}]]]))
  
  (defn add-file-results-page
    [{:keys [file user]}]
    (let [id (db/add-file-to-db file user)]
      (page/html5
       (gen-page-head "Added a File")
       header-links
       [:h1 "Added a File"]
       [:p "Added ["file", "user"] (id: " id ") to the db. "
        [:a {:href (str "/file/" id)} "See for yourself"]
        "."])))
  
  (defn file-page
    [file-id]
    (let [{file :file user :user} (db/get-file file-id)]
      (page/html5
       (gen-page-head (str "File: " file-id))
       header-links
       [:h1 "A Single File"]
       [:p "id: " file-id]
       [:p "File: " file]
       [:p "User: " user])))
  
  (defn all-files-page
    []
    (let [all-files (db/get-all-files)]
      (page/html5
       (gen-page-head "All Files in the db")
       header-links
       [:h1 "All Files"]
       [:table
        [:tr [:th "id"] [:th "file"] [:th "user"]]
        (for [file all-files]
          [:tr [:td (:id file)] [:td (:file file)] [:td (:user file)]])])))