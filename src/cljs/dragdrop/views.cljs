(ns dragdrop.views
  (:require [re-frame.core :as rf]))

(defn dragable-list [{:keys [on-reorder]
                      :or {on-reorder (fn [])}}
                     & items]
  (let [ _ (rf/dispatch [:init-order-index (range (count items))])
        items-vec (vec items)
        drag-index (rf/subscribe [:drag-index])
        order-index (rf/subscribe [:order-index])]
    (fn []
      [:ul
       (doall
         (for [[index position] (map-indexed vector @order-index)]
           ^{:key index}
           [:li {:draggable true
                 :on-drag-start #(rf/dispatch [:set-drag-index index])
                 :on-drag-over (fn [e]
                                 (.preventDefault e)
                                 (rf/dispatch [:set-drag-over-index position])
                                 (rf/dispatch [:swap-position items-vec position @drag-index]))
                 :on-drag-leave #(rf/dispatch [:drag-over-nothing])
                 :on-drag-end (fn []
                                (rf/dispatch [:clean-drag-n-drop])
                                (on-reorder (map items-vec @order-index)))}
            (get items-vec index)]))])))

(defn ui []
  (let [all-state (rf/subscribe [:all-state])]
    [:div
     [dragable-list
      {:on-reorder (fn [order-index]
                     (rf/dispatch [:set-order-index order-index]))}
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
