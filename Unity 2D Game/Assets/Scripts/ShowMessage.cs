using UnityEngine;

public class PrikazPoruke : MonoBehaviour
{
    public GameObject porukaUI; // Referenca na UI poruku
    public GameObject porukaTagType;

    void Start()
    {
        if (porukaUI != null)
        {
            porukaUI.SetActive(false); // Sakrij poruku na poèetku
        }
    }

    public void ShowMessage(string tagType)
    {
        if (porukaUI != null)
        {
            porukaUI.SetActive(true);
            porukaTagType.SetActive(true);

            var tmpComponent = porukaTagType.GetComponent<TMPro.TMP_Text>();
            if (tmpComponent != null)
            {
                tmpComponent.text = tagType; 
            }
        }
    }

    public void HideMessage()
    {
        if (porukaUI != null)
        {
            porukaUI.SetActive(false); // Sakrij poruku
        }

        if (porukaTagType != null)
        {
            porukaTagType.SetActive(false); // Sakrij specifiènu poruku

            // Resetiraj tekst unutar porukaTagType
            var tmpComponent = porukaTagType.GetComponent<TMPro.TMP_Text>();
            if (tmpComponent != null)
            {
                tmpComponent.text = ""; // Oèisti tekst
            }
        }
    }

}

