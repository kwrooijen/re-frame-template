(ns {{ns-name}}.core
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]{{#re-pressed?}}
   [re-pressed.core :as rp]{{/re-pressed?}}{{#breaking-point?}}
   [breaking-point.core :as bp]{{/breaking-point?}}
   [{{ns-name}}.events :as events]{{#routes?}}
   [{{ns-name}}.routes :as routes]{{/routes?}}
   [{{ns-name}}.views :as views]
   [{{ns-name}}.config :as config]
   ))


(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn init []{{#routes?}}
  (routes/app-routes){{/routes?}}
  (re-frame/dispatch-sync [::events/initialize-db]){{#re-pressed?}}
  (re-frame/dispatch-sync [::rp/add-keyboard-event-listener "keydown"]){{/re-pressed?}}{{#breaking-point?}}
  (re-frame/dispatch-sync [::bp/set-breakpoints
                           {:breakpoints [:mobile
                                          768
                                          :tablet
                                          992
                                          :small-monitor
                                          1200
                                          :large-monitor]
                            :debounce-ms 166}]){{/breaking-point?}}
  (dev-setup)
  (mount-root))
