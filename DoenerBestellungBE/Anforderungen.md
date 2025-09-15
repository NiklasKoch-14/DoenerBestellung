# Doenerbestellungsapp

## Funktionale Anforderungen
- Signup Login yes
- Liste des Döners Menü yes
- Benutzer kann seine Standard bestellung angeben yes
- Beseated anwesenheiten werden gezogen 
- Dönerbestellungsliste wird erstellt
- Dönerbestellungsliste wird gruppiert
- Paypal link und rechnung werden an die Dönerbestellungsliste gepackt





        //generates Map with username and corresponding defaultOrder
        Map<String, Order> defaultOrdermap = new HashMap<>();
        users.forEach(user -> {
            defaultOrdermap.put(user.getUsername(), user.getDefaultOrder());
        });


        List<OrderDTO> orders = new ArrayList<>();
        for (Map.Entry<String, Order> entry : defaultOrdermap.entrySet()) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setUsername(entry.getKey());
            orderDTO.setProducts(mapToProductDTOList(getProductsFromOrder(entry.getValue())));
            orders.add(orderDTO);
        }

        log.info(orders.toString());