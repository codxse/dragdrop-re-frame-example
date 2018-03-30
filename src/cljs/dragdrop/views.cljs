(ns dragdrop.views
  (:require [re-frame.core :as rf]
            [reagent.core :as r]))

(defn put-before [items pos item]
  (let [items (remove #{item} items)
        head (take pos items)
        tail (drop pos items)]
    (concat head [item] tail)))

(defn draggable-list [& items]
  (let [_ (rf/dispatch [:set-order-index (range (count items))])
        items-vec (vec items)
        order-index (rf/subscribe [:order-index])
        drag-index (rf/subscribe [:drag-index])]
        ;s (r/atom {:order (range (count items-vec))})]
    (fn []
      [:div
       [:ul
        (doall
          (for [[i pos] (map vector @order-index (range))]
            [:li
             {:key i
              :style {:border (when (= i @drag-index)
                                "1px solid blue")}
              :draggable true
              :on-drag-start #(rf/dispatch [:set-drag-index i])
              :on-drag-over (fn [e]
                              (.preventDefault e)
                              (rf/dispatch [:set-drag-over pos])
                              (rf/dispatch [:swap-position put-before pos @drag-index]))
              :on-drag-leave #(rf/dispatch [:drag-over-nothing])
              :on-drag-end (fn []
                             (rf/dispatch [:clean-drag-n-drop])
                             (map items-vec @order-index))}
             (get items-vec i)]))]])))

(defn ui []
  (let [state (rf/subscribe [:state])]
    (fn []
      [:div
       [draggable-list
        "a"
        "b"
        "c"
        "d"]
       [:h3 "UI"]
       [:pre (with-out-str (cljs.pprint/pprint @state))]])))


(defn main-panel []
  (let [name (rf/subscribe [:name])]
    (fn []
      [:div [ui]])))
