(ns omnipotent.macros
  "Clojure macro's to be evaluated at compile time")

(def ^:dynamic *cljs-debug* true)

(defmacro fn->
  "Equivalent to `(fn [x] (-> x ~@body))"
  [& body]
  `(fn [x#] (-> x# ~@body)))

(defmacro fn->>
  "Equivalent to `(fn [x] (->> x ~@body))"
  [& body]
  `(fn [x#] (->> x# ~@body)))

(defmacro <-
  "Converts a ->> to a ->

   (->> (range 10) (map inc) (<- doto prn) (reduce +))

   Jason W01fe is happy to give a talk anywhere any time on
   the calculus of arrow macros.

   Note: syntax modified from original."
  ([x] `(~x))
  ([cmd & body]
      `(~cmd ~(last body) ~@(butlast body))))

(defmacro ann
  "Log the execution of the provided s-exp's with title"
  [title & sexps]
  (if *cljs-debug*
    `(do (println ~title)
         ~@sexps)
    `(do ~@sexps)))

(defmacro annt
  "Log the execution of the provided s-exp's with title, and output the value af the sexp"
  [title sexp]
  (if *cljs-debug*
    `(do (println (str ~title "=> " ~sexp))
         ~sexp)
    ~sexp))

(defmacro annv
  "Log the execution of the provided s-exp's with title, and output value of any variables provided"
  [title vars & sexps]
  (if *cljs-debug*
    (loop [vars vars, agg []]
      (if vars
        (let [v (first vars)
              new-agg (conj agg `(str '~v "=" ~v))]
          (recur (next vars) new-agg))
        (let [details (interpose ", " agg)]
          `(do (println (str ~title "=> " ~@details))
               ~@sexps)
          )))
    `(do ~@sexps)))






