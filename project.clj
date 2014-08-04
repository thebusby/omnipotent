(defproject omnipotent "1.0.0-SNAPSHOT"
  :description "A project demonstrating the use of Clojurescript and Om"
  :url "https://github/thebusby/omnipotent/"
  :license {:name "MIT"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.6.0"]

                 ;; cljs libs
                 [org.clojure/clojurescript "0.0-2280"]
                 [org.clojure/core.async "0.1.303.0-886421-alpha"]
                 [om "0.7.0"]
                 [om-sync "0.1.1"] ;; Add's support for :tx-listen on om.core/root
                 [sablono "0.2.20"] ;; Enable Hiccup to work with FB's Reach
                 [shoreleave/shoreleave-browser "0.3.0"] ;; Provides access to Cookies, History, Storage, Blob fn's
                 ]

  :plugins [[lein-cljsbuild "1.0.3"]]
  :source-paths ["src/clj"]
  :cljsbuild
  {:builds [{:id "dev"
             :source-paths ["src/cljs"]
             :compiler {:output-to  "nginx/html/js/omnipotent.js"
                        :output-dir "target/out-dev"
                        ;; :source-map true
                        :optimizations :whitespace
                        :pretty-print true}
             }
            {:id "compact"
             :source-paths ["src/cljs"]
             :compiler {:output-to  "nginx/html/js/omnipotent.js"
                        :output-dir "target/out-compact"
                        :optimizations :simple
                        :pretty-print true}
             }]}

  :profiles
  {:dev
   {:dependencies [[com.cemerick/piggieback "0.1.3"]]
    :resource-paths ["config" "resources"]
    :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}
   :uberjar {:aot :all}})
