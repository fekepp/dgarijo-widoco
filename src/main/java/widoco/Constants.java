/*
 *  Copyright 2012-2013 Ontology Engineering Group, Universidad Politecnica de Madrid, Spain

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package widoco;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import widoco.entities.Agent;
import widoco.entities.Ontology;


/**
 *
 * @author Daniel Garijo
 */
public class Constants {
    //constants for the Licensius service
    public static final String licensiusURIServiceLicense = "http://www.licensius.com/api/license/findlicenseinrdf?uri=";//"http://licensius.appspot.com/getLicense?content=";
    public static final String licensiusURIServiceLicenseInfo = "http://www.licensius.com/api/license/getlicenseinfo?uri=";//"http://licensius.appspot.com/getLicenseTitle?content=";
    public static final int licensiusTimeOut = 10000;
    
    public static final int oopsTimeOut = 30000;
    
    public static final String[] vocabPossibleSerializations = {"application/rdf+xml","text/turtle","text/n3"};
    /**
     * Constants for the  Step 2 (table)
     */
    
    public static final String abstractSectionContent="abstract";
    public static final String ontTitle="ontologyTitle";
    public static final String ontName="ontologyName";
    public static final String ontPrefix="ontologyPrefix";
    public static final String ontNamespaceURI="ontologyNamespaceURI";
    public static final String dateOfRelease="dateOfRelease";
    public static final String thisVersionURI="thisVersionURI";
    public static final String latestVersionURI="latestVersionURI";
    public static final String previousVersionURI="previousVersionURI";
    public static final String ontologyRevision="ontologyRevisionNumber";
    public static final String authors="authors";
    public static final String authorsURI="authorsURI";
    public static final String authorsInstitution="authorsInstitution";
    public static final String authorsInstitutionURI="authorsInstitutionURI";
    public static final String contributors="contributors";
    public static final String contributorsURI="contributorsURI";
    public static final String contributorsInstitution="contributorsInstitution";
    public static final String contributorsInstitutionURI="contributorsInstitutionURI";
    public static final String publisher="publisher";
    public static final String publisherURI="publisherURI";
    public static final String publisherInstitution="publisherInstitution";
    public static final String publisherInstitutionURI="publisherInstitutionURI";
    public static final String importedOntologyNames="importedOntologyNames";
    public static final String importedOntologyURIs="importedOntologyURIs";
    public static final String extendedOntologyNames="extendedOntologyNames";
    public static final String extendedOntologyURIs="extendedOntologyURIs";
    public static final String licenseName="licenseName";
    public static final String licenseURI="licenseURI";
    public static final String licenseIconURL="licenseIconURL";
    public static final String citeAs="citeAs";
    public static final String doi="DOI";
    public static final String rdf="RDFXMLSerialization";
    public static final String ttl="TurtleSerialization";
    public static final String n3="N3Serialization";
    public static final String json="JSONLDSerialization";
//    public static final String deafultSerialization="deafultSerialization";
    public static final String status="status";
    
    public static final String opening= "<!DOCTYPE html>\n<html prefix=\"dc: http://purl.org/dc/terms/ schema: http://schema.org/ prov: http://www.w3.org/ns/prov# foaf: http://xmlns.com/foaf/0.1/ owl: http://www.w3.org/2002/07/owl#\">\n"
            + "<head>\n"
            + "<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\" />\n";
    //missing specialization. Missing alternate
    
    public static String  getAbstractSection(String abstractContent, Configuration c, Properties langFile){
        String abstractSection = "<h2>"+langFile.getProperty("abstract")+"</h2><p>";
        if(abstractContent!=null && !"".equals(abstractContent)){
            abstractSection+=abstractContent;
        }
        else{
            abstractSection+=langFile.getProperty("abstractPlaceHolder");
        }
        return abstractSection;
    }
    
    /**
     * Text representing the div of the status.
     * @param c
     * @return 
     */
    public static String getStatus(Configuration c){
        String html = "";
        if(c.getMainOntology().getStatus()!=null && !c.getMainOntology().getStatus().equals("")){
            html+="<div class=\"status\">\n"
                    + "<div>\n"
                    + "<span>"+c.getMainOntology().getStatus()+"</span>\n</div>\n</div>";
        }
        return html;
    }
            
    public static String getIntroductionSection(Configuration c, Properties lang){
        String s = "<h2 id=\"intro\" class=\"list\">"+lang.getProperty("introPlaceHolder");
        return s;
    }
    
    public static String getReferencesSection(Configuration c, Properties lang){
        String s ="<h2 id=\"ref\" class=\"list\">"+lang.getProperty("referencesPlaceHolder");
        return s;
    }
    public static String getAcknowledgementsSection(Configuration c, Properties lang){
        String s = "<div id=\"acknowledgements\">\n"+
                    "<h2 id=\"ack\" class=\"list\">"+lang.getProperty("ackText");
        return s;
    }
    public static String getChangeLogSection(Properties lang){
        return lang.getProperty("changeLog");
    }
    public static final String ending="</body></html>";
    
    //given a list of agents, this method gets it as a String
    private static String getAgents(ArrayList<Agent> auth){
        String agents ="";
        try{
            Iterator<Agent> it = auth.iterator();
            int i = 1;
            while(it.hasNext()){
                Agent currAuth = it.next();
                String authorName = currAuth.getName(); //the name should be always there
                if(authorName==null || "".equals(authorName)){
                    authorName = "Agent"+i;
                    i++;
                }
                if(currAuth.getURL()!=null &&!"".equals(currAuth.getURL())){
                    agents+="<dd><a property=\"dc:creator schema:author prov:wasAttributedTo\" resource=\""+currAuth.getURL()+"\" href=\""+currAuth.getURL()+"\">"+authorName+"</a>";
                }else{
                    agents+="<dd>"+authorName;
                }
                if(currAuth.getInstitutionName()!=null && !"".equals(currAuth.getInstitutionName())){
                    if(currAuth.getInstitutionURL()!=null && !"".equals(currAuth.getInstitutionURL())){
                        agents+=", (<a href=\""+currAuth.getInstitutionURL()+"\">"+currAuth.getInstitutionName()+"</a>)";
                    }else{
                        agents+=", "+currAuth.getInstitutionName();
                    }
                }else{
                    if(currAuth.getInstitutionURL()!=null && !"".equals(currAuth.getInstitutionURL())){
                        agents+=", (<a href=\""+currAuth.getInstitutionURL()+"\">"+currAuth.getInstitutionURL()+"</a>)";
                    }
                }
                agents+="</dd>";
            }   
        }catch(Exception e){
            System.out.println("Error while writing authors, their urls or their instititions.");
        }
        return agents;
    }
    private static String getAuthors(ArrayList<Agent> auth, Properties l) {
        String a="<dl><dt>"+l.getProperty("authors")+"</dt>\n";
        //the same amount of names and institutions is assumed.
        a+=getAgents(auth);
        return a +"</dl>\n";                   
    }
    
    private static String getContributors(ArrayList<Agent> contrib, Properties l) {
        String c="<dl><dt>"+l.getProperty("contributors")+"</dt>\n";
        c+=getAgents(contrib);
        c = c.replace("dc:creator schema:author", "dc:contributor schema:contributor");//fix annotations
        return c +"</dl>\n";                   
    }
    
    private static String getPublisher (Agent publisher, Properties l){
        if(publisher.getName()!=null &&
                !"".equals(publisher.getName()) && !"".equals(publisher.getURL())){
            if(publisher.getName()!=null && !"".equals(publisher.getName())){
                publisher.setName(publisher.getURL());
            }
            String c="<dl><dt>"+l.getProperty("publisher")+"</dt>\n";
            ArrayList<Agent> p = new ArrayList<Agent>();
            p.add(publisher);
            c+=getAgents(p);
            c = c.replace("dc:creator schema:author", "dc:publisher");//fix annotations
            return c +"</dl>\n";                   
        }
        return "";
    }

    //method for extracting the ontologies from an arraylist.
    private static String getOntologies(ArrayList<Ontology> ontos){
        String ontologies = "";
        Iterator<Ontology> it = ontos.iterator();
        int i=1;
        while(it.hasNext()){
            Ontology currentOnto = it.next();
            String currentOntoName = currentOnto.getName();
            if(currentOntoName==null||"".equals(currentOntoName)){
                currentOntoName = "Onto"+i;
                i++;
            }
            if(currentOnto.getNamespaceURI()!=null && !"".equals(currentOnto.getNamespaceURI())){
                ontologies+="<dd><a property=\"owl:imports schema:mentions\" resource=\""+currentOnto.getNamespaceURI()+"\" href=\""+currentOnto.getNamespaceURI()+"\">"+currentOntoName+"</a></dd>";
            }
            else{
                ontologies+="<dd>"+currentOntoName+"</dd>";
            }
        }
        return ontologies;
    }
    private static String getImports(ArrayList<Ontology> ontos, Properties l) {
        String imports= "<dl><dt>"+l.getProperty("imported")+"</dt>\n";
        imports+= getOntologies(ontos);
        return imports+"</dl>\n";
    }

    private static String getExtends(ArrayList<Ontology> ontos, Properties l) {
        String extended= "<dl><dt>"+l.getProperty("extended")+"</dt>\n";   
        extended += getOntologies(ontos);
        extended = extended.replace("owl:imports",""); //to remove the import annotation
        return extended+"</dl>\n";
    }
    
    public static String getNameSpaceDeclaration(HashMap<String,String> namesp, Properties lang){
    	String ns="<div id=\"namespacedeclarations\">\n"+
        "<h3 id=\"ns\" class=\"list\">"+lang.getProperty("ns")+lang.getProperty("nsText") ;
        Iterator<String> keys = namesp.keySet().iterator();
        while(keys.hasNext()){
            String current = keys.next();
            ns+="<tr><td><b>"+current+"</b></td><td>&lt;"+namesp.get(current)+"&gt;</td></tr>\n";
        }
        ns+="</tbody>\n"+
          "</table>\n"+
          "</div>\n"+
        "</div>\n";
    	return ns;
    }
    
    public static String getIndexDocument(String resourcesFolderName,Configuration c, LODEParser l, Properties lang){
        String document=opening;
        if(c.isUseW3CStyle()){
            document+= " <link rel=\"stylesheet\" href=\""+resourcesFolderName+"/primer.css\" media=\"screen\" />   " +
                     " <link rel=\"stylesheet\" href=\""+resourcesFolderName+"/rec.css\" media=\"screen\" />   " +
                     " <link rel=\"stylesheet\" href=\""+resourcesFolderName+"/extra.css\" media=\"screen\" />   " +
                     " <link rel=\"stylesheet\" href=\""+resourcesFolderName+"/owl.css\" media=\"screen\" />   ";
                     
        }else{
            document+= " <link rel=\"stylesheet\" href=\""+resourcesFolderName+"/yeti.css\" media=\"screen\" />   " +
                     " <link rel=\"stylesheet\" href=\""+resourcesFolderName+"/site.css\" media=\"screen\" />";
        }
        document += "<script src=\""+resourcesFolderName+"/jquery.js\"></script> \n" +
                    "<script src=\""+resourcesFolderName+"/marked.min.js\"></script> \n" +
                     "    <script> \n" +
                     "function loadHash() {\n" +
                     "  jQuery(\".markdown\").each(function(el){jQuery(this).after(marked(jQuery(this).text())).remove()});\n" +
                     "	var hash = location.hash;\n" +
                     "	if($(hash).offset()!=null){\n" +
                     "	  $('html, body').animate({scrollTop: $(hash).offset().top}, 0);\n"+
                     "}\n" +
                     "	loadTOC();\n"+
                     "}"
                     + "function loadTOC(){\n" +
                    "	//process toc dynamically\n" +
                    "	  var t='<h2>Table of contents</h2><ul>';i = 1;j=0;\n" +
                    "	  jQuery(\".list\").each(function(){\n" +
                    "		if(jQuery(this).is('h2')){\n" +
                    "			if(j>0){\n" +
                    "				t+='</ul>';\n" +
                    "				j=0;\n" +
                    "			}\n" +
                    "			t+= '<li>'+i+'. <a href=#'+ jQuery(this).attr('id')+'>'+ jQuery(this).text()+'</a></li>';\n" +
                    "			i++;\n" +
                    "		}\n" +
                    "		if(jQuery(this).is('h3')){\n" +
                    "			if(j==0){\n" +
                    "				t+='<ul>';\n" +
                    "			}\n" +
                    "			j++;\n" +
                    "			t+= '<li>'+(i-1)+'.'+j+'. '+'<a href=#'+ jQuery(this).attr('id')+'>'+ jQuery(this).text()+'</a></li>';\n" +
                    "		}\n" +
                    "		t = t.replace(' "+lang.getProperty("back3").replace("&iacute;", "�")+"','');\n" +//back to ToC
                    "	  });\n" +
                    "	  t+='</ul>';\n" +
                    "	  $(\"#toc\").html(t); \n" +
                    "}"+
                     "    $(function(){\n";
        if(c.isIncludeAbstract()) document += "      $(\"#abstract\").load(\"sections/abstract-"+c.getCurrentLanguage()+".html\"); \n";
        if(c.isIncludeIntroduction()) document += "      $(\"#introduction\").load(\"sections/introduction-"+c.getCurrentLanguage()+".html\"); \n";
        if(c.isIncludeOverview()) document += "      $(\"#overview\").load(\"sections/overview-"+c.getCurrentLanguage()+".html\"); \n";
        if(c.isIncludeDescription()) document += "      $(\"#description\").load(\"sections/description-"+c.getCurrentLanguage()+".html\"); \n";
        if(c.isIncludeReferences()) document += "      $(\"#references\").load(\"sections/references-"+c.getCurrentLanguage()+".html\"); \n";
        if(c.isIncludeCrossReferenceSection()) document += "      $(\"#crossref\").load(\"sections/crossref-"+c.getCurrentLanguage()+".html\", null, loadHash); \n";
            document+="    });\n" +
                     "    </script> \n" +
                     "  </head> \n" +
                     "\n" +
                    //missing specialization. Missing alterante
                    //I assume the namespace prefix of the ontology is provided
                    "<body resource=\""+c.getMainOntology().getNamespaceURI()+"\" typeOf=\"owl:Ontology schema:TechArticle\">\n"+
//                //RDF-a Annotations
                     "<span resource=\"\" typeOf=\"foaf:Document schema:WebPage\">\n";
        if(c.getMainOntology().getReleaseDate()!=null && !"".equals(c.getMainOntology().getReleaseDate())){
            document+="<span property=\"dc:created schema:dateCreated\" content=\""+c.getMainOntology().getReleaseDate()+"\"></span>\n";
         }
        if(c.getMainOntology().getLatestVersion()!=null && !"".equals(c.getMainOntology().getLatestVersion())){
            document+="<span property=\"dc:isVersionOf prov:specializationOf\" resource=\""+c.getMainOntology().getLatestVersion()+"\"></span>\n";
        }
        if(c.getMainOntology().getPreviousVersion()!=null && !"".equals(c.getMainOntology().getPreviousVersion())){
            document+="<span property=\"prov:alternateOf prov:revisionOf\" resource=\""+c.getMainOntology().getPreviousVersion()+"\"></span>\n";
        }
            document+="<span property=\"dc:contributor prov:wasAttributedTo schema:contributor\" resource=\"http://purl.org/net/dgarijo\"></span>\n"+
                        "</span>\n";
        document += getHeadSection(c, lang);
        document += getStatus(c);
        if(c.isIncludeAbstract()) document += "     <div id=\"abstract\"></div>\n";
        document += "<div id=\"toc\"></div>";
        if(c.isIncludeIntroduction()) document += "     <div id=\"introduction\"></div>\n";
        //else document += "<div id=\"namespacedeclaration\"></div>\n";
        if(c.isIncludeOverview()) document += "     <div id=\"overview\"></div>\n";
        if(c.isIncludeDescription()) document += "     <div id=\"description\"></div>\n";
        if(c.isIncludeCrossReferenceSection()) document +=                 "     <div id=\"crossref\"></div>\n";
        if(c.isIncludeReferences()) document += "     <div id=\"references\"></div>\n";
              document+= getAcknowledgementsSection(c, lang)+"</body> \n" +
                        "</html>";
        return document;
    }
    
    public static String getHeadSection(Configuration c, Properties l){
        String head = "<div class=\"container\">"
                + "<div class=\"head\">\n";
        head+="<div style=\"float:right\">language ";
        Iterator <String> lang = c.getLanguagesToGenerateDoc().iterator();
        while(lang.hasNext()){
            String nextLang = lang.next();
            head +="<a href=\"index-"+nextLang+".html\"><b>"+nextLang+"</b></a> ";
        }
        head+="</div>\n";
        if(c.getMainOntology().getTitle()!=null &&!"".equals(c.getMainOntology().getTitle()))
            head+="<h1 property=\"dc:title schema:name\">"+c.getMainOntology().getTitle()+"</h1>\n";
        if(c.getMainOntology().getReleaseDate()!=null && !"".equals(c.getMainOntology().getReleaseDate()))
            head+="<span property=\"dc:modified schema:dateModified\" content=\""+c.getMainOntology().getReleaseDate()+"\"></span>\n"+
                    "<h2>"+l.getProperty("date")+" "+c.getMainOntology().getReleaseDate()+"</h2>\n";
        if(c.getMainOntology().getThisVersion()!=null && !"".equals(c.getMainOntology().getThisVersion()))
            head+="<dl>\n"+
                    "<dt>"+l.getProperty("thisVersion")+"</dt>\n"+
                    "<dd><a href=\""+c.getMainOntology().getThisVersion()+"\">"+c.getMainOntology().getThisVersion()+"</a></dd>\n"+
                    "</dl>";
        if(c.getMainOntology().getLatestVersion()!=null && !"".equals(c.getMainOntology().getLatestVersion()))
            head+="<dl><dt>"+l.getProperty("latestVersion")+"</dt>\n"+
                    "<dd><a href=\""+c.getMainOntology().getLatestVersion()+"\">"+c.getMainOntology().getLatestVersion()+"</a></dd>\n"+
                    "</dl>";
        if(c.getMainOntology().getPreviousVersion()!=null && !"".equals(c.getMainOntology().getPreviousVersion()))
            head+= "<dl>\n"+
                    "<dt>"+l.getProperty("previousVersion")+"</dt>\n"+
                    "<dd><a property=\"schema:significantLink prov:wasRevisionOf\" href=\""+c.getMainOntology().getPreviousVersion()+"\">"+c.getMainOntology().getPreviousVersion()+"</a></dd>\n"+
                    "</dl>\n";
        if(c.getMainOntology().getRevision()!=null && !"".equals(c.getMainOntology().getRevision()))
            head +="<dt>"+l.getProperty("revision")+"</dt>\n"+
                    "<dd property=\"schema:version\">"+c.getMainOntology().getRevision()+"</dd>\n";
        if(!c.getMainOntology().getCreators().isEmpty())
            head += getAuthors(c.getMainOntology().getCreators(),l)+"\n";
        if(!c.getMainOntology().getContributors().isEmpty())
            head += getContributors(c.getMainOntology().getContributors(),l)+"\n";
        if(c.getMainOntology().getPublisher()!=null){
            head += getPublisher(c.getMainOntology().getPublisher(), l);
            /*
            String publisherName = c.getPublisher().getName();
            String publisherURL = c.getPublisher().getURL();
            if(publisherURL == null ||publisherURL.equals("")){
                publisherURL = "http://example.org/insertPublisherURIHere";
            }
            if(publisherName == null || publisherName.equals("")){
                publisherName = publisherURL;
            }
            //avoid including a publisher by default
            if(!publisherName.equals("http://example.org/insertPublisherURIHere") || 
                    !publisherURL.equals("http://example.org/insertPublisherURIHere")){
                head += "<dl><dt>"+l.getProperty("publisher")+"</dt>"+"\n"
                        + "<dd><a href="+publisherURL+" target=\"_blank\">"+publisherName+"</a></dd></dl>\n";
            }*/
        }
        if(!c.getMainOntology().getImportedOntologies().isEmpty())
            head += getImports(c.getMainOntology().getImportedOntologies(),l)+"\n";
        if(!c.getMainOntology().getExtendedOntologies().isEmpty())
            head += getExtends(c.getMainOntology().getExtendedOntologies(),l)+"\n";
        
        HashMap<String,String> availableSerializations = c.getMainOntology().getSerializations();
        head+="<dl><dt>"+l.getProperty("serialization")+"</dt><dd>";
        for(String serialization:availableSerializations.keySet()){
            head+="<span><a href=\""+availableSerializations.get(serialization)+"\" target=\"_blank\"><img src=\"https://img.shields.io/badge/Format-"+serialization.replace("-", "_")+"-blue.svg\" alt=\""+serialization+"\"></img></a> </span>";
        }
        
        head+="</dd></dl>";
        if(c.getMainOntology().getLicense()!=null){
            String lname = c.getMainOntology().getLicense().getName();//"license name goes here";
            String licenseURL = c.getMainOntology().getLicense().getUrl();//"http://insertlicenseURIhere.org";
            if(licenseURL == null || "".equals(licenseURL))licenseURL = l.getProperty("licenseURLIfNull");
            if(lname == null || "".equals(lname)) lname = l.getProperty("licenseIfNull");
            head+="<dl><dt>"+l.getProperty("license")+"</dt><dd>"
                    + "<a rel=\"license\" href=\""+licenseURL+"\" target=\"_blank\"><img src =\"https://img.shields.io/badge/License-"+lname.replace("-", "_")+"-blue.svg\" alt=\""+licenseURL+"\"></img></a>\n"+
                    "<span property=\"dc:license\" resource=\""+licenseURL+"\"></span>\n";
            if(c.getMainOntology().getLicense().getIcon()!=null && !"".equals(c.getMainOntology().getLicense().getIcon())){
                head+="<a property=\"dc:rights\" href=\""+licenseURL+"\" rel=\"license\" target=\"_blank\">\n" +
                "<img src=\""+c.getMainOntology().getLicense().getIcon()+"\" style=\"border-width:0\" alt=\"License\"></img>\n" +
                "</a>\n<br/>";
            }
            head+="</dd></dl>";
        }
        //add lang tags here
        if(c.isCreateWebVowlVisualization()){
            head+="<dl><dt>"+l.getProperty("visualization")+"</dt>"
                + "<dd>"
                + "<a href=\"http://vowl.visualdataweb.org/webvowl/index.html#iri="+c.getMainOntology().getNamespaceURI()+"\" target=\"_blank\"><img src=\"https://img.shields.io/badge/Visualize_with-WebVowl-blue.svg\" alt=\"Visualize with WebVowl\"></img></a>"
                + "</dd>"
                + "</dl>\n";
        }
        if(!"".equals(c.getMainOntology().getCiteAs()) && c.getMainOntology().getCiteAs()!=null){
            head+="<dl><dt>"+l.getProperty("citeAs")+"</dt>\n<dd>"+c.getMainOntology().getCiteAs()+"</dd>\n</dl>\n";
        }
        if(!"".equals(c.getMainOntology().getDoi()) && c.getMainOntology().getDoi()!=null){
            //doi is common for all languages
            head+="<dl><dt>DOI:</dt>\n<dd><a href=\"http://dx.doi.org/"+c.getMainOntology().getDoi()+"\"><img src =\"https://img.shields.io/badge/DOI-"+c.getMainOntology().getDoi()+"-blue.svg\" alt=\""+c.getMainOntology().getDoi()+"\"></img></a></dd>\n</dl>\n";
        }
        if(c.isPublishProvenance()){
            head+="<dl><a href=\"provenance/provenance-"+c.getCurrentLanguage()+".html\" target=\"_blank\">"+l.getProperty("provHead")+"</a></dl>";
        }
        head+= "<hr/>\n"+
                "</div>\n";
        return head;
    }
    
    public static String getOverviewSection(Configuration c, Properties lang){
        return "<h2 id=\"overv\" class=\"list\">"+c.getMainOntology().getName()+": "+lang.getProperty("overviewPlaceHolder");
    }
    
    public static String getDescriptionSection(Configuration c, Properties lang){
        return "<h2 id=\"desc\" class=\"list\">"+c.getMainOntology().getName()+": "+lang.getProperty("descriptionPlaceHolder");
    }
    
    public static String getCrossReferenceSection(Configuration c, Properties lang){
        return "<h2  id=\"crossreference\" class=\"list\">"+lang.getProperty("crossRefTitle")+" "+c.getMainOntology().getName()+" "+lang.getProperty("crossRefTitle2")+"</h2>"+"\n" +
               lang.getProperty("crossRefPlaceHolder")+c.getMainOntology().getName()+".\n";
    }
    
    public static String getProvenanceHtml(Configuration c, Properties lang){
        String provhtml = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\" />\n" +
                " \n" +
                "  </head> \n" +
                "\n" +
                "<body>\n" +
                "<div class=\"head\">\n";
        String provURI = c.getMainOntology().getThisVersion();
        if(provURI==null || provURI.equals("")){
            provURI = c.getDocumentationURI();
        }
        if(c.getMainOntology().getTitle()!=null &&!"".equals(c.getMainOntology().getTitle())){
            provhtml+="<h1>"+lang.getProperty("prov1")+" "+c.getMainOntology().getTitle()+" "+lang.getProperty("prov2")+" ("+provURI+")</h1>\n";
        }
        provhtml+="<ul>\n";
        if(!c.getMainOntology().getCreators().isEmpty()){
            provhtml+="	<li>"+lang.getProperty("createdBy")+" :\n";
            Iterator<Agent> creators = c.getMainOntology().getCreators().iterator();
            while(creators.hasNext()){
                Agent currCreator = creators.next();
                provhtml+= " "+currCreator.getName()+" ("+currCreator.getInstitutionName()+"),";
            }
            provhtml+="</li>";
        }
        if(!c.getMainOntology().getContributors().isEmpty()){
            provhtml+="	<li>"+lang.getProperty("contribBy")+":\n";
            Iterator<Agent> contrib = c.getMainOntology().getContributors().iterator();
            while(contrib.hasNext()){
                Agent currContrib = contrib.next();
                provhtml+= " "+currContrib.getName()+" ("+currContrib.getInstitutionName()+"),";
            }
            provhtml+="</li>\n";
        }
        if(c.getMainOntology().getLatestVersion()!=null &&!"".equals(c.getMainOntology().getLatestVersion())){
            provhtml+="<li>"+provURI+ " "+lang.getProperty("spec")+" "+ c.getMainOntology().getLatestVersion()+"</li>\n";
        }
        if(c.getMainOntology().getPreviousVersion()!=null &&!"".equals(c.getMainOntology().getPreviousVersion())){
            provhtml+="<li>"+provURI+ " "+lang.getProperty("rev")+" "+ c.getMainOntology().getPreviousVersion()+"</li>\n";
        }                    
        provhtml+="<li>"+lang.getProperty("result");
        if(c.getMainOntology().getReleaseDate()!=null &&!"".equals(c.getMainOntology().getReleaseDate())){
            provhtml+="<li>"+lang.getProperty("generated") +" "+c.getMainOntology().getReleaseDate();
        }
        provhtml+="</ul>\n" +
        "</div>\n<p>"+lang.getProperty("back")+" <a href=\"..\\index-"+c.getCurrentLanguage()+".html\">"+lang.getProperty("back1")+"</a>. <a href=\"provenance-"+c.getCurrentLanguage()+".ttl\">"+lang.getProperty("back2")+"</a></p>" +
        "</div>\n</body> \n" +
        "</html>";
        return provhtml;
    }
    
    public static String getProvenanceRDF(Configuration c){
        String provURI = c.getMainOntology().getThisVersion();
        if(provURI==null || provURI.equals("")){
            provURI = "..\\index-"+c.getCurrentLanguage()+".html";
        }
        String provrdf = "@prefix prov: <http://www.w3.org/ns/prov#> .\n"
                + "@prefix dc: <http://purl.org/dc/terms/> .\n"
                + "@prefix foaf: <http://xmlns.com/foaf/0.1/> .\n";
                provrdf+="<"+provURI+"> a prov:Entity;\n";
                if(c.getMainOntology().getTitle()!=null &&!"".equals(c.getMainOntology().getTitle())){
                    provrdf+= "\t dc:title \""+c.getMainOntology().getTitle()+"\";\n";
                }
                if(!c.getMainOntology().getCreators().isEmpty()){
                    Iterator<Agent> creators = c.getMainOntology().getCreators().iterator();
                    while(creators.hasNext()){
                        //me quedo aqui. Hay que cambiar todo. Quizas la responsabilidad puedo pasar, o asumir que todos los agentes itenen uris. Si no es un rollo
                        Agent currCreator = creators.next();
                        if(currCreator.getURL()!=null && !"".equals(currCreator.getURL())){
                            provrdf+= "\t prov:wasAttributedTo <"+currCreator.getURL()+">;\n";
                            provrdf+= "\t dc:creator <"+currCreator.getURL()+">;\n";
                        }else{
                            provrdf+= "\t prov:wasAttributedTo [ a prov:Agent; foaf:name \""+currCreator.getName()+"\".];\n";
                        }
                    }
                }
                if(!c.getMainOntology().getContributors().isEmpty()){
                    Iterator<Agent> contrib = c.getMainOntology().getContributors().iterator();
                    while(contrib.hasNext()){
                        Agent currContrib = contrib.next();
                        if(currContrib.getURL()!=null && !"".equals(currContrib.getURL())){
                            provrdf+= "\t prov:wasAttributedTo <"+currContrib.getURL()+">;\n";
                            provrdf+= "\t dc:contributor <"+currContrib.getURL()+">;\n";
                        }else{
                            provrdf+= "\t prov:wasAttributedTo [ a prov:Agent; foaf:name \""+currContrib.getName()+"\".];\n";
                        }
                    }
                }
                provrdf+= "\t prov:wasAttributedTo <https://github.com/dgarijo/Widoco/>,<http://www.essepuntato.it/lode/>;\n";
                if(c.getMainOntology().getLatestVersion()!=null &&!"".equals(c.getMainOntology().getLatestVersion())){
                    provrdf+="\t prov:specializationOf <"+c.getMainOntology().getLatestVersion()+">;\n";
                }
                if(c.getMainOntology().getPreviousVersion()!=null &&!"".equals(c.getMainOntology().getPreviousVersion())){
                    provrdf+="\t prov:wasRevisionOf <"+c.getMainOntology().getPreviousVersion()+">;\n";
                }                    
                if(c.getMainOntology().getReleaseDate()!=null &&!"".equals(c.getMainOntology().getReleaseDate())){
                    provrdf+="\t prov:wasGeneratedAt \""+c.getMainOntology().getReleaseDate()+"\";\n";                    
                }
                provrdf +=".\n";
        return provrdf;
    }
     
    public static final String lodeResources= "/lode.zip";
    public static final String oopsResources = "/oops.zip";
    
    public static final String configPath = "config"+File.separator+"config.properties";
    
    public static String getEvaluationText(String evaluationContent, Configuration c){
        String eval = "<!DOCTYPE html>\n" +
        "<html lang=\"en\">\n" +
        "  <head>\n" +
        "    <meta charset=\"UTF-8\">\n" +
        "    <title>"+c.getMainOntology().getTitle()+"</title>\n" +
        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
        "    <meta name=\"description\" content=\"Evaluation of the ontology with the OOPS tool.\">\n" +
        "    <meta name=\"Languaje\" content=\"English\">\n" +
        "    \n" +
        "    <script src=\"evaluation/jquery-1.11.0.js\"></script>\n" +
        "    <script src=\"evaluation/bootstrap.min.js\"></script>\n" +
        "    <link rel=\"stylesheet\" href=\"evaluation/style.css\" type=\"text/css\" media=\"print, projection, screen\" />\n" +
        "    <script type=\"text/javascript\" src=\"evaluation/jquery.tablesorter.min.js\"></script>\n" +
        "    <script type=\"text/javascript\" id=\"js\">\n" +
        "	    $(document).ready(function() \n" +
        "		    { \n" +
        "		    	$(\"#tablesorter-demo\").tablesorter(); \n" +
        "		    	$('.collapse').collapse({ \n" +
        "		    	toggle: false\n" +
        "		    	});\n" +
        "		    } \n" +
        "	    ); \n" +
        "    </script>\n" +
        "\n" +
        "    <link href=\"evaluation/bootstrap.css\" rel=\"stylesheet\">\n" +
        "    <style type=\"text/css\">\n" +
        "      body {\n" +
        //"        padding-top: 60px;\n" +
        "        padding-bottom: 40px;\n" +
        "      }\n" +
        "    </style>\n" +
        "    <link href=\"evaluation/bootstrap-responsive.css\" rel=\"stylesheet\">\n" +
        "    \n" +
        "    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->\n" +
        "    <!--[if lt IE 9]>\n" +
        "      <script src=\"/dist/js/html5shiv.js\"></script>\n" +
        "    <![endif]-->\n" +
        "\n" +
        "  </head>\n"
        + "<div class=\"container\">\n" +
            "<h1> <a href=\""+c.getOntologyURI()+"\" target=\"_blank\">"+c.getMainOntology().getTitle()+"</a></h1>\n" +
            "<br>\n" +
            "<dl class=\"dl-horizontal\">\n" +
            "<dt>Title</dt>\n" +
            "<dd><a href=\""+c.getOntologyURI()+"\" target=\"_blank\">"+c.getMainOntology().getTitle()+"</a></dd>\n" +
            "<dt>URI</dt>\n" +
            "<dd><a href=\""+c.getOntologyURI()+"\" target=\"_blank\">"+c.getOntologyURI()+"</a></dd>\n" +
            "<dt>Version</dt>\n" +
            "<dd>"+c.getMainOntology().getRevision()+"</dd>\n" +
            "</dl>"+
            "<p> The following evaluation results have been generated by the <a href = \"http://oops-ws.oeg-upm.net/\" target=\"_blank\">RESTFul web service</a> provided by <a href = \"http://oops.linkeddata.es\" target=\"_blank\">OOPS! (OntOlogy Pitfall Scanner!)</a>.</p>" +
            "<p>\n" +
            "<a href=\"http://oops.linkeddata.es\" target=\"_blank\"><img src=\"http://vocab.linkeddata.es/ontologies/oops/logomini.png\" alt=\"OOPS! logo\" class=\"img-rounded\" class=\"img-responsive\" /></a>"+
            "It is obvious that not all the pitfalls are equally important; their impact in the ontology " +
            "will depend on multiple factors. For this reason, each pitfall has an importance level " +
            "attached indicating how important it is. We have identified three levels:" +
            "</p>\n" +
            "\n" +
            "<dl class=\"dl-horizontal\">\n" +
            "<dt><span class=\"label label-danger\">Critical</span></dt>\n" +
            "<dd>It is crucial to correct the pitfall. Otherwise, it could affect the ontology consistency, reasoning, applicability, etc.</dd>\n" +
            "\n" +
            "<dt><span class=\"label label-warning\">Important</span></dt> <dd> Though not critical for ontology function, it is important to correct this type of pitfall.</dd>\n" +
            "\n" +
            "<dt><span class=\"label label-minor\">Minor</span></dt> <dd>It is not really a problem, but by correcting it we will make the ontology nicer.</dd>\n" +
            "</dl>"+
             evaluationContent+
            //references
            "<p>References:</p>\n"+
            "    <ul>\n"+
            "    <li>\n"+
            "    [1] G&oacute;mez-P&oacute;rez, A. Ontology Evaluation. Handbook on Ontologies. S. Staab and R. Studer Editors. Springer. International Handbooks on Information Systems. Pp: 251-274. 2004.\n"+
            "    </li> \n"+
            "    <li>\n"+
            "    [2] Noy, N.F., McGuinness. D. L. Ontology development 101: A guide to creating your first ontology. Technical Report SMI-2001-0880, Standford Medical Informatics. 2001.\n"+
            "    </li> \n"+
            "    <li>\n"+
            "    [3] Rector, A., Drummond, N., Horridge, M., Rogers, J., Knublauch, H., Stevens, R.,; Wang, H., Wroe, C. ''Owl pizzas: Practical experience of teaching owl-dl: Common errors and common patterns''. In Proc. of EKAW 2004, pp: 63-81. Springer. 2004.\n"+
            "    </li>\n"+
            "    <li>\n"+
            "    [4] Hogan, A., Harth, A., Passant, A., Decker, S., Polleres, A. Weaving the Pedantic Web. Linked Data on the Web Workshop LDOW2010 at WWW2010 (2010).\n"+
            "    </li>\n"+
            "     <li>\n"+
            "    [5] Archer, P., Goedertier, S., and Loutas, N. D7.1.3 - Study on persistent URIs, with identification of best practices and recommendations on the topic for the MSs and the EC. Deliverable. December 17, 2012.\n"+
            "    </li>\n"+
            "    <li>\n"+
            "    [6] Heath, T., Bizer, C.: Linked data: Evolving the Web into a global data space (1st edition). Morgan &amp; Claypool (2011).\n"+
            "    </li>\n"+
            "    </ul>\n"+    
            //copy footer here
            "<footer>\n" +
            "            <div class=\"row\">\n" +
            "    	<div class=\"col-md-7\">\n" +
            "    		Developed by 	        <a href = \"http://delicias.dia.fi.upm.es/members/mpoveda/\" target=\"_blank\">Mar&iacutea Poveda</a>\n" +
            "	        <br>\n" +
            "    	Built with <a target=\"_blank\" href=\"http://getbootstrap.com/\">Bootstrap</a>\n" +
            "	        <br>\n" +
            "           Integration with Widoco by <a href=\"https://w3id.org/people/dgarijo\">Daniel Garijo</a>"+
            "	        <br>\n" +
            "        </div>\n" +
            "    	<div class=\"col-md-5\">\n" +
            "		<p class=\"text-right\"> Developed with: </p>\n" +
            "		<p class=\"text-right\">\n" +
            "     		<a href=\"http://oops.linkeddata.es\" target=\"_blank\"><img src=\"http://vocab.linkeddata.es/ontologies/oops/logomini.png\" alt=\"OOPS! logo\" class=\"img-rounded\" class=\"img-responsive\" /></a>\n" +
            "    	</p>\n" +
            "    	</div>\n" +
            "      </div>\n" +
            "      </footer>\n" +
            "    </div> <!-- /container -->\n";
        return eval;
    }
    
    /**
     * Method that writes an htaccess file according to the W3C best practices.
     * Note that hash is different than slash
     * @param c
     * @return 
     */
    public static final String getHTACCESS(Configuration c){
        String projectFolder = c.getDocumentationURI().substring(c.getDocumentationURI().lastIndexOf(File.separator)+1);
        String htAccessFile = "# Turn off MultiViews\n" +
        "Options -MultiViews\n" +
        "\n" +
        "# Directive to ensure *.rdf files served as appropriate content type,\n" +
        "# if not present in main apache config\n" +
        "AddType application/rdf+xml .rdf\n" +
        "AddType application/rdf+xml .owl\n" +
        "AddType text/turtle .ttl\n" +
        "AddType application/n-triples .n3\n" +
        "AddType application/ld+json .json\n" +
        "# Rewrite engine setup\n" +
        "RewriteEngine On\n"+
        "#Change the path to the folder here\n"+
        "RewriteBase /"+projectFolder+" \n\n";
        
        htAccessFile+="# Rewrite rule to serve HTML content from the vocabulary URI if requested\n" +
        "RewriteCond %{HTTP_ACCEPT} !application/rdf\\+xml.*(text/html|application/xhtml\\+xml)\n" +
        "RewriteCond %{HTTP_ACCEPT} text/html [OR]\n" +
        "RewriteCond %{HTTP_ACCEPT} application/xhtml\\+xml [OR]\n" +
        "RewriteCond %{HTTP_USER_AGENT} ^Mozilla/.*\n";
        //this depends on whether the vocab is hash or slash!
        if(c.getMainOntology().isHashOntology()){
            htAccessFile +="RewriteRule ^$ index-"+c.getCurrentLanguage()+".html [R=303,L]\n\n";
            HashMap<String,String> serializations = c.getMainOntology().getSerializations();
            for(String serialization:serializations.keySet()){
                htAccessFile +="# Rewrite rule to serve "+serialization+" content from the vocabulary URI if requested\n";
                if(serialization.equals("RDF/XML")){
                    htAccessFile+="RewriteCond %{HTTP_ACCEPT} \\*/\\* [OR]\n" +
                            "RewriteCond %{HTTP_ACCEPT} application/rdf\\+xml\n";
                }else if(serialization.equals("TTL")){
                    htAccessFile+="RewriteCond %{HTTP_ACCEPT} text/turtle [OR]\n" +
                        "RewriteCond %{HTTP_ACCEPT} text/\\* [OR]\n" +
                        "RewriteCond %{HTTP_ACCEPT} \\*/turtle \n";
                }else if(serialization.equals("N-Triples")){
                    htAccessFile+="RewriteCond %{HTTP_ACCEPT} application/n-triples\n";
                }else if (serialization.equals("JSON-LD")){
                    htAccessFile+="RewriteCond %{HTTP_ACCEPT} application/ld+json\n";
                }
                htAccessFile +="RewriteRule ^$ "+serializations.get(serialization)+" [R=303,L]\n\n";
            }
            htAccessFile += "RewriteCond %{HTTP_ACCEPT} .+\n" +
                "RewriteRule ^$ 406.html [R=406,L]\n"
                + "# Default response\n" +
                "# ---------------------------\n" +
                "# Rewrite rule to serve the RDF/XML content from the vocabulary URI by default\n" +
                "RewriteRule ^$ "+serializations.get("RDF/XML")+" [R=303,L]";    
        }else{//slash (the structure changes a little)
            String warning = "############################################################################\n"
                           + "### THIS FILE SHOULD BE PLACED ON THE PARENT FOLDER OF THE DOCUMENTATION ###\n"
                           + "### OTHERWISE THE CONTENT NEGOTIATION WILL NOT WORK                      ###\n"
                           + "### THE URL OF YOUR VOCABULARY WILL BE (domain)/"+projectFolder+"/def    ###\n"
                           + "############################################################################\n";
            htAccessFile = warning + htAccessFile;
            htAccessFile +="RewriteRule ^def$ doc/index-"+c.getCurrentLanguage()+".html [R=303,L]\n";
            htAccessFile +="RewriteCond %{HTTP_ACCEPT} !application/rdf\\+xml.*(text/html|application/xhtml\\+xml)\n" +
                "RewriteCond %{HTTP_ACCEPT} text/html [OR]\n" +
                "RewriteCond %{HTTP_ACCEPT} application/xhtml\\+xml [OR]\n" +
                "RewriteCond %{HTTP_USER_AGENT} ^Mozilla/.*\n" +
                "RewriteRule ^def/(.+) doc/index-"+c.getCurrentLanguage()+".html#$1 [R=303,NE,L]\n";
            HashMap<String,String> serializations = c.getMainOntology().getSerializations();
            for(String serialization:serializations.keySet()){
                htAccessFile +="# Rewrite rule to serve "+serialization+" content from the vocabulary URI if requested\n";
                String normalSerialization, complexSerialization, condition="";
                if(serialization.equals("RDF/XML")){
                    condition = "RewriteCond %{HTTP_ACCEPT} \\*/\\* [OR]\n" +
                            "RewriteCond %{HTTP_ACCEPT} application/rdf\\+xml\n";
                }else if(serialization.equals("TTL")){
                    condition ="RewriteCond %{HTTP_ACCEPT} text/turtle [OR]\n" +
                        "RewriteCond %{HTTP_ACCEPT} text/\\* [OR]\n" +
                        "RewriteCond %{HTTP_ACCEPT} \\*/turtle \n";
                }else if(serialization.equals("N-Triples")){
                    condition ="RewriteCond %{HTTP_ACCEPT} application/n-triples\n";
                }else if (serialization.equals("JSON-LD")){
                    condition = "RewriteCond %{HTTP_ACCEPT} application/ld+json\n";
                }
                normalSerialization = "RewriteRule ^def$ doc/"+serializations.get(serialization)+" [R=303,L]\n\n";
                complexSerialization = "RewriteRule ^def/(.+)$ doc/"+serializations.get(serialization)+" [R=303,NE,L]\n\n";
                htAccessFile += condition+normalSerialization+condition+complexSerialization;
            }
            htAccessFile += "RewriteCond %{HTTP_ACCEPT} .+\n" +
                "RewriteRule ^def$ doc/406.html [R=406,L]\n"
                + "# Default response\n" +
                "# ---------------------------\n" +
                "# Rewrite rule to serve the RDF/XML content from the vocabulary URI by default\n" +
                "RewriteRule ^def$ doc/"+serializations.get("RDF/XML")+" [R=303,L]";
        }
        
        return htAccessFile;
        
    }

    /**
     * Text for the 406 page
     * @param c 
     * @param lang 
     * @return  the content of the 406 page
     */
    public static String get406(Configuration c, Properties lang) {
        String page406 = "<!DOCTYPE HTML PUBLIC \"-//IETF//DTD HTML 2.0//EN\">\n" +
            "<html><head>\n" + lang.getProperty("notAccPage")+"<ul>";
        HashMap<String,String> serializations = c.getMainOntology().getSerializations();
        page406+="<li><a href=\"index-"+c.getCurrentLanguage()+".html\">html</a></li>";
        for(String s:serializations.keySet()){
            page406+="<li><a href=\""+serializations.get(s)+"\">"+s+"</a></li>";
        }
        page406+= "</ul>\n" +
            "\n" +
            "</body></html>";
        return page406;
    }
    
}
