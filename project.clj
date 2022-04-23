(defproject vpn-watcher "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [etaoin "0.4.6"]
                 [rewrite-clj "1.0.767-alpha"]
                 [com.taoensso/timbre "4.10.0"]]
  :main ^:skip-aot vpn-watcher.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
