(ns omnipotent.macros
  "Clojure macro's to be evaluated at compile time")

(def ^:dynamic *cljs-debug* true)

(def ^:dynamic *ann-header* "")

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

(defmacro ^{:private true} if-lets*
  [bindings then else]
  (let [form (subvec bindings 0 2)
        more (subvec bindings 2)]
    (if (empty? more)
      `(if-let ~form
         ~then
         ~else)
      `(if-let ~form
         (if-lets* ~more ~then ~else)
         ~else))))

(defmacro if-lets
  "Like if-let, but accepts multiple bindings and evaluates them sequentally.
   binding evaluation halts on first falsey value, and 'else' clause activates."
  ([bindings then]
     `(if-lets ~bindings ~then nil))
  ([bindings then else]
     (cond
      (not (even? (count bindings))) (throw (IllegalArgumentException. "if-lets requires an even number of bindings"))
      (not (vector? bindings))       (throw (IllegalArgumentException. "if-lets requires a vector for its binding"))
      :else `(if-lets* ~bindings ~then ~else))))

(defmacro ann
  "Log the execution of the provided s-exp's with title"
  [title & sexps]
  (if cljs-debug
    `(do (println (str *ann-header* ~title))
         ~@sexps)
    `(do ~@sexps)))

(defmacro annv
  "Log the execution of the provided s-exp's with title, and output value of any variables provided"
  [title vars & sexps]
  (if cljs-debug
    (loop [vars vars, agg []]
      (if vars
        (let [v (first vars)
              new-agg (conj agg `(str '~v "=" ~v))]
          (recur (next vars) new-agg))
        (let [details (interpose ", " agg)]
          `(do (println (str *ann-header* ~title "=> " ~@details))
               ~@sexps)
          )))
    `(do ~@sexps)))






