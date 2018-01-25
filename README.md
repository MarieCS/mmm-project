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
* __M :__ Liste des évenements (liste : RecyclerView, ev : CardView)
* __A :__ Carte interactive (évenements sur la carte)
* Recherche des évenements (lieu, thème, date, mot clés) > Popup évenement
___
* __K :__ Fiche de l'évenement
* Lier l'app avec les mails, agenda, téléphone, réseau social, itinéraire...)
* Evaluation des évenements 
___

* Créer le rôle Organisateur > MAJ du taux de remplissage d'un lieu
* Créer des parcours entre évenements > Publier les parcours
* Une fonction supplémentaire
