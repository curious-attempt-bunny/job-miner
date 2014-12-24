(def site "https://frozen-forest-9048.herokuapp.com")
(def site "http://localhost:5000")

(require '[net.cgrand.enlive-html :as html])

(def content (html/html-resource (java.net.URL. "http://ch.tbe.taleo.net/CH01/ats/careers/searchResults.jsp?org=EXTENSIS&cws=1")))
(def entries (html/select content [:table#cws-search-results :tr]))

(->> entries
    (filter
        (fn [el] 
            (some
                #(= "Portland, OR" (first %))
                (map :content (html/select el [:td :b])))))
    (map #(first (html/select % [:a])))
    (map #(array-map
            :title (clojure.string/trim (first (:content %)))
            :url   (-> % :attrs :href)))
    (map #(str
        site
        "/jobs?url="
        (java.net.URLEncoder/encode (:url %))
        "&title="
        (java.net.URLEncoder/encode (:title %))))
    (pmap #(client/post %))
)
