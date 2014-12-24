(ns jobagator2015.core)
(def site "https://frozen-forest-9048.herokuapp.com")
(def site "http://localhost:5000")

(require '[net.cgrand.enlive-html :as html])

(def content (html/html-resource (java.net.URL. "https://hire.jobvite.com/CompanyJobs/Careers.aspx?c=qLY9Vfwx&jvresize=https://www.jivesoftware.com/wp-content/themes/jive2015/functions/frameresize.htm")))
(def entries (html/select content [:.joblist :tr]))

(->> entries
    (remove #(nil? (html/select % [:a.jvjoblink])))
    (filter #(= "Portland" (first (:content (last (html/select % [:td]))))))
    (map #(first (html/select % [:a.jvjoblink])))
    (map #(array-map
            :title (first (:content %))
            :url   (-> % :attrs :href)))
    (map #(str
        site
        "/jobs?url="
        (java.net.URLEncoder/encode (:url %))
        "&title="
        (java.net.URLEncoder/encode (:title %))))
    (pmap #(client/post %))
)





