(ns easyopmanager.servlet
  (:gen-class
   :extends javax.servlet.http.HttpServlet)
  (:import (java.io PrintWriter) 
           (com.google.appengine.api.datastore Query))
  (:require [appengine-clj.datastore :as ds])
  (:use clojure.contrib.str-utils))

(defn create [content author]
  (ds/create {:kind "Greeting" :author author :content content :date (java.util.Date.)}))

(defn find-all []
  (ds/find-all (doto (Query. "Greeting") (.addSort "date"))))
(defn show[]
  (str-join "<br />" (map :content (find-all))))

(defn -doGet [this request response]
  (create "some data" "Mary")
  (.setContentType response "text/html")
  (let [out (.getWriter response)]
    (doto out
      (.println "<html>")
      (.println "<head><title>Hello Clojure!</title></head>")
      (.println "<body>")
      (.println "<h1>Hello Clojure!</h1>")
						(.println (show))
      (.println "</body>")
      (.println "</html>"))))


