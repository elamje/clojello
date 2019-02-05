(ns clojello.handler
  (:require [clojello.views :as views]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
  (GET "/" 
      [] 
      (views/home-page))
  (GET "/add-file"
      []
      (views/add-file-page))
  (POST "/add-file"
      {params :params}
      (views/add-file-results-page params)) 
  (GET "/file/:file-id"
      [file-id]
      (views/file-page file-id))
  (GET "/all-files"
      []
      (views/all-files-page))  
  (route/resources "/")    
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
