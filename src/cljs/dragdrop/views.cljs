(ns dragdrop.views
  (:require [re-frame.core :as rf]))

(defn dragable-list [& items]
  (let [ _ (rf/dispatch [:init-order-index (range (count items))])
        order-index (rf/subscribe [:order-index])]
    (fn [& items]
      [:ul
       (doall
         (for [i @order-index]
           ^{:key i}
           [:li {:draggable true
                 :on-drag-start #(rf/dispatch [:set-drag-index i])
                 :on-drag-end #(rf/dispatch [:clean-drag-n-drop])
                 :on-drag-over (fn [e]
                                 (.preventDefault e)
                                 (rf/dispatch [:set-drag-over-index i]))
                 :on-drag-leave #(rf/dispatch [:drag-over-nothing])}

            (nth (vec items) i)]))])))

(defn ui []
  (let [all-state (rf/subscribe [:all-state])]
    [:div
     [dragable-list
      "a"
      "b"
      "c"
      "d"]
     [:div->pre
      (with-out-str (cljs.pprint/pprint @all-state))]]))


(defn main-panel []
  (let [name (rf/subscribe [:name])]
    (fn []
      [:div [ui]])))
