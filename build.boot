(configure-repositories!
 (fn [m]
   (merge m (some (fn [[regex cred]] (if (re-find regex (:url m)) cred))
              (gpg-decrypt
                (clojure.java.io/file
                  (System/getProperty "user.home") ".lein/credentials.clj.gpg")
                :as :edn)))))


(def +version+ (->> (slurp "gradle.properties")
                    (re-find #"version=(.*)")
                    second))


(task-options!
  push {:file (format "build/libs/jsass-%s.jar" +version+)
        :pom  "build/poms/pom-default.xml"
        :repo "clojars"})
