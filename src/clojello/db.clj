(ns clojello.db
    (:require [clojure.java.jdbc :as jdbc]))
  
  (def db-spec {:dbtype "h2" :dbname "./clojello"})
  
  (defn add-file-to-db
    [file user]
    (let [results (jdbc/insert! db-spec :files {:file file :user user})]
      (assert (= (count results) 1))
      (first (vals (first results)))))
  
  (defn get-file
    [file-id]
    (let [results (jdbc/query db-spec
                              ["select file where id = ?" file-id])]
      (assert (= (count results) 1))
      (first results)))
  
  (defn get-all-files
    []
    (jdbc/query db-spec "select id, file, user from files"))


