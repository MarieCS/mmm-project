# Projet MMM
Ajouter google_maps_api.xml dans app/src/debug/res/values/

```xml
<resources>
    	<string name="google_maps_key" templateMergeStrategy="preserve" translatable="false"> 
		KEY
	</string>
</resources>
```

Lien des données pour Firebase (Choisir GeoJSON ou JSON) : 
	
	https://www.data.gouv.fr/fr/datasets/programme-de-la-fete-de-la-science-2017/

## TODO
* OK : Liste des évenements (liste : RecyclerView, ev : CardView)
* EC : Carte interactive (évenements sur la carte)
* EC : Recherche des évenements (lieu, thème, date, mot clés) > Popup évenement
___
* OK : Fiche de l'évenement
* OK : Lier l'app avec les mails, agenda, téléphone, réseau social, itinéraire...)
* OK : Evaluation des évenements 
___

* OK : Créer le rôle Organisateur > MAJ du taux de remplissage d'un lieu
* EC : Créer des parcours entre évenements > Publier les parcours
* ---- : Une fonction supplémentaire
